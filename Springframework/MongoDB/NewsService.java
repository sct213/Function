package poly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.stanford.nlp.pipeline.CoreSentence;
import poly.dto.NewsDTO;
import poly.persistance.mapper.INewsMapper;
import poly.service.INewsService;
import poly.util.NLPUtil;
import poly.util.WebCrawler;

@Service("NewsService")
public class NewsService implements INewsService {

	@Resource(name = "NewsMapper")
	private INewsMapper newsMapper;

	// 로그파일 출력개체
	private Logger log = Logger.getLogger(this.getClass());

	// 웹크롤링한 뉴스 DB에 저장
	@Override
	public int SaveNews(String title, String inputText, String newsUrl, String newsname) throws Exception {

		log.info(this.getClass().getName() + "saveNews start");

		NewsDTO pDTO = new NewsDTO();
		
		pDTO.setNews_title(title);
		pDTO.setNews_contents(inputText);
		pDTO.setNews_url(newsUrl);
		pDTO.setNews_name(newsname);

		log.info("nDTO : " + pDTO.getNews_title());
		log.info("nDTO : " + pDTO.getNews_contents());
		log.info("nDTO : " + pDTO.getNews_url());
		log.info("nDTO : " + pDTO.getNews_name());

		int saved = newsMapper.InsertNewsInfo(pDTO);
		pDTO = null;
		
		return saved;
	}

	@Override
	@Scheduled(cron="0 0 7 ? * *")
	public void scheduleCrawl() throws Exception{
		int res = 0;
		
		log.info(this.getClass().getName() + "crawlHerald() start");
		String[] crawlRes = WebCrawler.crawlHerald();
		String title = crawlRes[0];
		String inputText = crawlRes[1];
		String newsUrl = crawlRes[2];
		String newsname = "herald";
		
		log.info("title : "+title);
		log.info("inputText : "+inputText);
		log.info("newsUrl : "+newsUrl);
		res = SaveNews(title, inputText, newsUrl, newsname);
		log.info(this.getClass().getName() + "crawlHerald() end");
		
		
		log.info(this.getClass().getName() + "crawlbbc() start");
		String[] crawlRes1 = WebCrawler.crawluk();
		String title1 = crawlRes1[0];
		String inputText1 = crawlRes1[1];
		String newsUrl1 = crawlRes1[2];
		String newsname1 = "bbc";
		res += SaveNews(title1, inputText1, newsUrl1, newsname1);
		log.info(this.getClass().getName() + "crawlbbc() end");
		
		log.info(this.getClass().getName() + "crawltimes() start");
		String[] crawlRes2 = WebCrawler.crawltimes();
		String title2 = crawlRes2[0];
		String inputText2 = crawlRes2[1];
		String newsUrl2 = crawlRes2[2];
		String newsname2 = "times";
		res += SaveNews(title2, inputText2, newsUrl2, newsname2);
		log.info(this.getClass().getName() + "crawltimes() end");
		
		log.info(this.getClass().getName() + "crawlyonhap() start");
		String[] crawlRes3 = WebCrawler.crawlyonhap();
		String title3 = crawlRes3[0];
		String inputText3 = crawlRes3[1];
		String newsUrl3 = crawlRes3[2];
		String newsname3 = "yonhap";
		res += SaveNews(title3, inputText3, newsUrl3, newsname3);
		log.info(this.getClass().getName() + "crawlyonhap()) end");
		
		// 뉴스 결과 넣어주기
		// model.addAttribute("res", String.valueOf(res));

		log.info(this.getClass().getName() + ".getNewsInfoFromWEB End!");
		
	}
	
	public Map<String, Object> insertHerald() throws Exception{
		
		log.info(this.getClass().getName() + "crawlHerald() start");
		
		Map<String, Object> rMap = new HashMap<>();
		
		String[] crawlRes = WebCrawler.crawlHerald();
		String title = crawlRes[0];
		String inputText = crawlRes[1];

		Iterator<CoreSentence> newsContents = NLPUtil.sentence(inputText);
		
		List<String> pList = new ArrayList<>();

		while(newsContents.hasNext()) {
			CoreSentence nlpContents = newsContents.next();
			
			String stringNlp = nlpContents.toString();
		
			stringNlp += "#";
			// 문장을 끊기 좋게 일부로 넣은 것 
			pList.add(stringNlp);
		}
		
		List<String> lemmaList = NLPUtil.lemma(inputText);
		
		log.info("!!!!!!!!!!lemmas : " + lemmaList);
		
		rMap.put("newsLemmas", lemmaList);
		
		rMap.put("newsName", "KoreaHerald");
		
		rMap.put("newsTitle", title);
		
		rMap.put("newsContents", pList);
		
		log.info("insertHerald End!");
		
		return rMap;
	}
	
	public void insertMongoNews() throws Exception{
		
		log.info(this.getClass().getName() + "crawlHerald() start");
		
		String[] crawlRes = WebCrawler.crawlHerald();
		String title = crawlRes[0];
		String inputText = crawlRes[1];
		String newsUrl = crawlRes[2];
		
		log.info("title : "+title);
		log.info("inputText : "+inputText);
		log.info("newsUrl : "+newsUrl);
		
		log.info(this.getClass().getName() + "crawlHerald() end");
		
		log.info(this.getClass().getName() + "crawlbbc() start");
		
		String[] crawlRes1 = WebCrawler.crawluk();
		String title1 = crawlRes1[0];
		String inputText1 = crawlRes1[1];
		String newsUrl1 = crawlRes1[2];
		
		log.info(this.getClass().getName() + "crawlbbc() end");
		
		log.info(this.getClass().getName() + "crawltimes() start");
		
		String[] crawlRes2 = WebCrawler.crawltimes();
		String title2 = crawlRes2[0];
		String inputText2 = crawlRes2[1];
		String newsUrl2 = crawlRes2[2];
		
		log.info(this.getClass().getName() + "crawltimes() end");
		
		log.info(this.getClass().getName() + "crawlyonhap() start");
		
		String[] crawlRes3 = WebCrawler.crawlyonhap();
		String title3 = crawlRes3[0];
		String inputText3 = crawlRes3[1];
		String newsUrl3 = crawlRes3[2];

		log.info(this.getClass().getName() + "crawlyonhap()) end");
		
		// 뉴스 결과 넣어주기
		// model.addAttribute("res", String.valueOf(res));

		log.info(this.getClass().getName() + ".getNewsInfoFromWEB End!");
		
	}
	
	@Override
	public Map<String, Object> mongoCrawlNews() throws Exception{
		
		Map<String, Object> rMap = new HashMap<>();
		
		log.info(this.getClass().getName() + "crawlHerald() start");
		String[] crawlRes = WebCrawler.crawlHerald();
		String title = crawlRes[0];
		String inputText = crawlRes[1];
		String newsUrl = crawlRes[2];
		String newsname = "herald";
		
		log.info("title : "+title);
		log.info("inputText : "+inputText);
		log.info("newsUrl : "+newsUrl);

		log.info(this.getClass().getName() + "crawlHerald() end");
		
		
		log.info(this.getClass().getName() + "crawlbbc() start");
		String[] crawlRes1 = WebCrawler.crawluk();
		String title1 = crawlRes1[0];
		String inputText1 = crawlRes1[1];
		String newsUrl1 = crawlRes1[2];
		String newsname1 = "bbc";

		log.info(this.getClass().getName() + "crawlbbc() end");
		
		log.info(this.getClass().getName() + "crawltimes() start");
		String[] crawlRes2 = WebCrawler.crawltimes();
		String title2 = crawlRes2[0];
		String inputText2 = crawlRes2[1];
		String newsUrl2 = crawlRes2[2];
		String newsname2 = "times";
	
		log.info(this.getClass().getName() + "crawltimes() end");
		
		log.info(this.getClass().getName() + "crawlyonhap() start");
		String[] crawlRes3 = WebCrawler.crawlyonhap();
		String title3 = crawlRes3[0];
		String inputText3 = crawlRes3[1];
		String newsUrl3 = crawlRes3[2];
		String newsname3 = "yonhap";
	
		log.info(this.getClass().getName() + "crawlyonhap()) end");
		
		// 뉴스 결과 넣어주기
		// model.addAttribute("res", String.valueOf(res));

		log.info(this.getClass().getName() + ".getNewsInfoFromWEB End!");
		return rMap;
	}
	// DB에서 데이터 가져오기
	@Override
	public NewsDTO getNewsInfoFromDB(NewsDTO nDTO) {

		return newsMapper.getNewsInfoFromDB(nDTO);
	}

	
}
