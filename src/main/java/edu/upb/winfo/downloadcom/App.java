package edu.upb.winfo.downloadcom;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.upb.winfo.utils.DatabaseConnection;

/**
 * Created by geskill on 30.12.2015.
 *
 * crawler4j has some issues, that hinder the crawler to be more efficient
 * https://github.com/yasserg/crawler4j/issues/16
 *
 * https://github.com/yasserg/crawler4j/issues/108
 *
 * @author geskill
 */
public class App {
	public static void main(String[] args) throws Exception {

		// TODO: future versions implement a remote database to use the crawler from different tiers
		DatabaseInterface databaseInterface = new LocalDatabase("data/config/databaseConnection.xml");

		String crawlStorageFolder = "data/crawl/root/";
		int numberOfCrawlers = 7;

		CrawlConfig crawlConfig = new CrawlConfig();
		crawlConfig.setCrawlStorageFolder(crawlStorageFolder);
		crawlConfig.setResumableCrawling(true);

        /*
         * Instantiate the controller for this crawl.
         */
		PageFetcher pageFetcher = new PageFetcher(crawlConfig);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
		controller.addSeed("http://download.cnet.com/");
		controller.addSeed("http://download.cnet.com/windows/");
		controller.addSeed("http://download.cnet.com/mac/");
		controller.addSeed("http://download.cnet.com/webware/");
		controller.addSeed("http://download.cnet.com/ios/");
		controller.addSeed("http://download.cnet.com/android/");
		controller.addSeed("http://download.cnet.com/mobile/");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
		controller.start(DownloadComCrawler.class, numberOfCrawlers);
	}
}