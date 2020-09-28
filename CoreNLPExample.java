		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,lemma");
		props.setProperty("coref.algorithm", "neural");
		
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		// 분석 할 글들을 Document안에 넣어줌
		
		CoreDocument doc = new CoreDocument("Hello, my name is Adam. I have two sisters. I went to California");
		
		pipeline.annotate(doc);
		
		Iterator<CoreSetence> it = doc.sentences().iterator();
		
		while(it.hasNext()){
			CoreSentence sent = it.next();
			
			System.out.println(sent.text());
	}
}