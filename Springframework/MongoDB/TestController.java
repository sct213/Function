package poly.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import poly.persistance.mongo.IMongoTestMapper;
import poly.service.INewsService;
import poly.service.IUserService;

@Controller
public class TestController {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name = "UserService")
	IUserService userService;

	@Resource(name = "NewsService")
	INewsService newsService;

	@Resource(name = "MongoTestMapper")
	IMongoTestMapper mongoTestMapper;

	@RequestMapping(value = "mongoSelect", produces = "application/json; charset=UTF8")
	@ResponseBody
	public List<Map<String, Object>> select(HttpServletRequest request, Model model, HttpSession session)
			throws Exception {
		return mongoTestMapper.test();
	}

	@RequestMapping(value = "mongoSelectWithCondition", produces = "application/json; charset=UTF8")
	@ResponseBody
	public List<Map<String, Object>> selectWithCondition(HttpServletRequest request, Model model, HttpSession session)
			throws Exception {
		DBObject query = new BasicDBObject("name", "gildong");
		// name이 gildong인 데이터를 query변수에 대입
		
		return mongoTestMapper.selectWithCondition(query);
		// mongoTestMapper의 selectWithCondition에 query파라미터를 return
	}

	@RequestMapping(value = "mongoInsert", produces = "application/json; charset=UTF8")
	@ResponseBody
	public List<Map<String, Object>> insert(HttpServletRequest request, Model model, HttpSession session)
			throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("name", "chang0");
		// name key에 chang0을 put
		obj.put("age", 24);
		obj.put("sentence", new String[] { "gdgdgd", "12341234" });
		// sentence key 문자열 배열로 gdgdgd, 12341234를 put

		mongoTestMapper.insert(obj);
		// mongoTestMapper의 insert에 obj 파라미터를 대입

		return mongoTestMapper.test();
	}
	
	/**
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "insertHerald", produces ="application/json; charset=UTF8")
	@ResponseBody
	public List<Map<String, Object>> insertHerald(HttpServletRequest request, Model model, HttpSession session) 
			throws Exception{
		
		log.info("insertHerald Start!");
		
		// inserHerald메소드에 리턴값을 pMap에 대입 
		Map<String,Object> pMap = newsService.insertHerald();
		
		Map<String, Object> obj = new HashMap<>();
		
		obj.put("newsName", pMap.get("newsName"));
		log.info("newsName : " + pMap.get("newsName"));
		
		obj.put("newsTitle", pMap.get("newsTitle"));
		log.info("newsTitle : " + pMap.get("newsTitle"));

		
		// List로 한 문장씩 넣기  
		List<String> originalSentenceList = new ArrayList<>();
		
		String sent = (pMap.get("newsContents")).toString();
		
		String[] splitSent = sent.split("#");
		// 불필요한 앞 글자 하나를 제거한 문장을 rList에 대입 
				for(int i = 0; i<=splitSent.length-1; i++) {
					originalSentenceList.add(splitSent[i].substring(1));
					log.info(originalSentenceList.get(i));
				}
		
		obj.put("originalSentence", originalSentenceList); // 원형 한 문장씩 넣기 
		
		log.info("------------------------------");
		// lemma!!!!!
		List<String> lemmaList = new ArrayList<>();
		
		String lemma = (pMap.get("newsLemmas")).toString();
		
		String[] lemmas = lemma.split(" ");
		
		for(int i = 0; i<lemmas.length; i++) {
				
				lemmas[i] = lemmas[i].replace("\\(", "");
				lemmas[i] = lemmas[i].replace(")", "");
				lemmas[i] = lemmas[i].replace("[", "");
				lemmas[i] = lemmas[i].replace("#", "");
				lemmas[i] = lemmas[i].replace(";", "");
				lemmas[i] = lemmas[i].replace(",", "");
				lemmas[i] = lemmas[i].replace(".", "");
				lemmas[i] = lemmas[i].replace("-", "");
				
				lemmaList.add(lemmas[i]);
				log.info(lemmaList.get(i));
		}
		
		String time = getCurrentTime();
		
		log.info("######Time : " + time);
		obj.put("lemmas", lemmaList);
		
		log.info("------------------------------");
		// 문장을 단어별로 잘라 넣기 
		List<String> tokenList = new ArrayList<>();
		
		String[] tokenSent = sent.split(" ");
		
		// 단어에 불필요한 기호들 제거 후 tokenList에 대입
		for(int i = 0; i<tokenSent.length; i++) {
			
			tokenSent[i] = tokenSent[i].replace("\\(", "");
			tokenSent[i] = tokenSent[i].replace(")", "");
			tokenSent[i] = tokenSent[i].replace("[", "");
			tokenSent[i] = tokenSent[i].replace("#", "");
			tokenSent[i] = tokenSent[i].replace(";", "");
			tokenSent[i] = tokenSent[i].replace(",", "");
			tokenSent[i] = tokenSent[i].replace(".", "");
			
			tokenList.add(tokenSent[i]);
			
			log.info(tokenList.get(i));
		}
		
		obj.put("Tokens", tokenList); // 원형 단어 하나씩 넣기
		
		
		mongoTestMapper.insert(obj);
		
		log.info("insert Herald End!!");
		
		return null;
	}
	@RequestMapping(value = "mongoInsertNews", produces = "application/json; charset=UTF8")
	@ResponseBody
	public List<Map<String, Object>> insertNews(HttpServletRequest request, Model model, HttpSession session)
			throws Exception {
		Map<String, Object> obj = new HashMap<>();
		obj.put("title", "trump dies");
		obj.put("newsUrl", "www.trumpdies.com");
		// 임의로 값을 만들어 Key에 대입
		
		List<String> origSent = new ArrayList<>();
		origSent.add("trump dies");
		origSent.add("oh my god!!");
		// 임의 값을 만들어 List origSent에 값을 대입
		
		obj.put("originalSentences", origSent);
		// originalSentence key에 List형인 origSent를 put
		
		List<String[]> lemmas = new ArrayList<>();
		
		origSent.forEach(sentence ->{
			lemmas.add(sentence.split(" "));
		});
		
		obj.put("lemmas", lemmas);
		
		List<String> translation = new ArrayList<>();
		origSent.add("트럼프 뒤짐");
		origSent.add("헐~~");
		
		obj.put("translation", translation);
		
		mongoTestMapper.insert(obj);
		
	
		
		return null;
	}

	// 템플릿
	@RequestMapping(value = "template")
	public String template() {
		return "/template";
	}

	// ajax test
	@RequestMapping(value = "/hello/text")
	public String Hello() {

		return "/hello";
	}

	@ResponseBody
	@RequestMapping(value = "/hello/hello")
	public boolean Hello(HttpServletRequest request) {
		String value = request.getParameter("id");

		if (value.equals("헬로")) {
			return true;
		}

		return false;
	}
}
