package com.bbconsult.wealthmanagment.common;

import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.bbconsult.wealthmanagment.domain.models.ComponentProperties;
import com.bbconsult.wealthmanagment.domain.models.LoadXMLEvent;
import com.bbconsult.wealthmanagment.domain.models.PackageDirectory;
import com.bbconsult.wealthmanagment.domain.models.Report;
import com.bbconsult.wealthmanagment.domain.models.ReportMenuItems;
import com.bbconsult.wealthmanagment.domain.models.ReportStructure;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebViewReader {
    private static final String defaultUrl = "http://10.0.2.2:80/";
    private static WebViewReader instance;
    private final WebView invisibleWebView;
    ArrayList<PackageDirectory> packageDirectories = new ArrayList<>();
    ArrayList<String> links = new ArrayList<>();
    ArrayList<String> firstComponents = new ArrayList<>();
    private ScheduledExecutorService exec;
    private String url = "";
    private String webViewText = "";
    private boolean isWebViewLoaded = false;
    private ReportStructure reportStructure = new ReportStructure();

    WebViewReader(WebView webView, String url) {
        this.url = url;
        //invisibleWebView = new WebView(get);
        invisibleWebView = webView;
//        invisibleWebView.setVisibility(View.INVISIBLE);
        invisibleWebView.getSettings().setJavaScriptEnabled(true);
        invisibleWebView.setWebViewClient(new WebViewClient());
        invisibleWebView.loadUrl(url);
        System.out.println("Start Reading");
        System.out.println(url);
    }

    public static void initInstance(WebView webView, String url) {
        if (instance == null) {
            // Create the instance
            instance = new WebViewReader(webView, url);
        }
    }

    public static WebViewReader getInstance() {
        // Return the instance
        return instance;
    }

    private boolean isPackageDirectoryAdded(String[] packageElements, PackageDirectory packageDirectoryToAdd, ArrayList<PackageDirectory> packageDirectories) {
        String packageName = packageElements[0];
        for (PackageDirectory packageDirectory : packageDirectories) {

            if (packageName.equals(packageDirectory.getName())) {
                if (packageElements.length == 1 && packageDirectory.getChildItemList().size() != 0) {
                    for (PackageDirectory lastPackageDirectory : packageDirectory.getChildItemList()) {
                        if (packageDirectoryToAdd.getName().equals(lastPackageDirectory.getName())) {
                            return true;
                        }
                    }
                    return false;
                } else if (packageElements.length == 1 && packageDirectory.getChildItemList().size() == 0)
                    return false;
                return isPackageDirectoryAdded(Arrays.copyOfRange(packageElements, 1, packageElements.length), packageDirectoryToAdd,
                        packageDirectory.getChildItemList());


            }
        }
        return false;
    }

    private PackageDirectory getParentDirectory(String[] packageElements, ArrayList<PackageDirectory> packageDirectories, PackageDirectory parentDirectory) {

        for (PackageDirectory packageDirectory : packageDirectories) {

            String packageName = packageElements[0];
            if (packageName.equals(packageDirectory.getName())) {
                if (packageDirectory.getChildItemList().size() == 0 || packageElements.length == 2)
                    return packageDirectory;
                return getParentDirectory(Arrays.copyOfRange(packageElements, 1, packageElements.length),
                        packageDirectory.getChildItemList(), packageDirectory);
            }

        }
        return parentDirectory;
    }

    private void setPackageDirectories(ArrayList<String> links, ArrayList<String> firstComponents, ArrayList<PackageDirectory> packageDirectories) {
        if (links.size() == 0)
            return;

        String itemUrl = links.get(0);
        String firstComponent = firstComponents.get(0);
        String[] packageElements = itemUrl.split("/");
        for (int i = 0; i < packageElements.length; i++) {
            PackageDirectory packageDirectory = new PackageDirectory();
            packageDirectory.setName(packageElements[i]);
            packageDirectory.setType("category");
            if (i != 0) {
                for (int j = 0; j < i; j++) {
                    String packageName = packageElements[j];
                    packageDirectory.setParent(packageDirectory.getParent() + "/" + packageName);
                }
                packageDirectory.setParent(packageDirectory.getParent().substring(1));
            }
            if (i == 0) {
                boolean isPackageFound = false;
                packageDirectory.setParent("root");
                for (PackageDirectory packageDirectoryTwo : packageDirectories) {
                    if (packageDirectoryTwo.getName().equals(packageDirectory.getName()))
                        isPackageFound = true;
                }
                if (isPackageFound)
                    continue;
                packageDirectories.add(packageDirectory);
            } else if (i == packageElements.length - 1) {
                packageDirectory.setType("item");
                packageDirectory.setFirstComponent(firstComponent);
                getParentDirectory(packageElements, packageDirectories, null).getChildItemList().add(packageDirectory);
            } else {
                if (!isPackageDirectoryAdded(packageDirectory.getParent().split("/"), packageDirectory, packageDirectories)) {
                    PackageDirectory parentDirectory = getParentDirectory(packageElements, packageDirectories, null);
                    parentDirectory.getChildItemList().add(packageDirectory);
                }
            }
        }
        links.remove(itemUrl);
        firstComponents.remove(firstComponent);
        setPackageDirectories(links, firstComponents, packageDirectories);
    }

    public ArrayList<PackageDirectory> GetPackages() {
        return packageDirectories;
    }

    public void startReading() {
        System.out.println("Start Reading");
        exec = Executors.newScheduledThreadPool(1);

        exec.scheduleAtFixedRate(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

                invisibleWebView.post(new Runnable() {
                    @Override
                    public void run() {

                        readWebView();
                    }
                });
                System.out.println("Ticker: " + webViewText);
                if (isWebViewLoaded && url.endsWith("appxml")) {
                    buildStructureAppxml();
                    exec.shutdown();
                }
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void readWebView() {

        invisibleWebView.evaluateJavascript(
                "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String html) {
                        System.out.println("html");

                        if (((html.contains("data link")) || (html.contains("recentlyUsed")) || (html.contains("favourites"))) && !isWebViewLoaded) {
                            System.out.println(html);
                            webViewText = html;
                            isWebViewLoaded = true;
                            System.out.println("LOADED");
//                            invisibleWebView.setVisibility(View.VISIBLE);
                        } else {
                            System.out.println("NOT LOADED");
                        }
                    }
                });
    }


    private void buildStructureAppxml() {

        reportStructure = new ReportStructure();

        System.out.println("Building Structure" + webViewText);
        int indexStart = webViewText.indexOf("&lt;navigation&gt;");
        int indexEnd = webViewText.indexOf("&lt;/navigation&gt;");


        webViewText = webViewText.substring(indexStart, indexEnd);
        webViewText = webViewText.replaceAll("\n", "").replaceAll("\\t", "")
                .replaceAll("&lt;", "<").replaceAll("&gt;", ">");

        ArrayList<Report> reports = new ArrayList<>();
        Document doc = Jsoup.parse(webViewText, "", Parser.xmlParser());
        Elements nodes = doc.select("item");
        int counter = 0;
        for (Element node : nodes) {
            counter++;
            Report report = new Report();
            //ItemName
            String itemName = node.attr("name").replaceAll("\\\"", "").replaceAll("\\\\", "");
            String itemUrl = node.attr("link").replaceAll("\\\"", "").replaceAll("\\\\", "");
            report.setName(itemName);
            report.setUrlToReport(itemUrl);
            ArrayList<ComponentProperties> components = new ArrayList<>();
            //Item Children: Components
            Elements itemNodes = node.select("component");
            for (int i = 0; i < itemNodes.size(); i++) {
                Element itemNode = itemNodes.get(i);

                String componentType = i + 1 + "_" + itemNode.attr("type").replaceAll("\\\"", "")
                        .replaceAll("\\\\", "").replaceAll("/", "");
                String componentName = itemNode.attr("name").replaceAll("\\\"", "")
                        .replaceAll("\\\\", "").replaceAll("/", "");

                ComponentProperties compProperty = new ComponentProperties();
                compProperty.setComponentType(componentType);
                compProperty.setComponentName(componentName);
                components.add(compProperty);
            }
            report.setReportComponents(components);
            reports.add(report);

        }

        int addFirstItem = 0;
        for (Element node : nodes) {
            String itemUrl = node.attr("link").replaceAll("\\\"", "").replaceAll("\\\\", "");
            String firstComponent = node.attr("firstComponent").replaceAll("\\\"", "").replaceAll("\\\\", "");

            links.add(itemUrl);
            firstComponents.add(firstComponent);
        }
        setPackageDirectories(links, firstComponents, packageDirectories);
        System.out.println("END COUNTER " + counter);
        reportStructure.setReports(reports);
        if (counter > 0) {
            EventBus.getDefault().post(new ReportMenuItems(packageDirectories));
            EventBus.getDefault().post(new LoadXMLEvent(reports));
        }


    }
}