package adword.UpdateDisplay;
import java.io.FileReader;
/**
 * @author Naveen
 *
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.api.ads.adwords.axis.v201710.cm.AdGroup;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterion;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupCriterionServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupOperation;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupReturnValue;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupServiceInterface;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupStatus;
import com.google.api.ads.adwords.axis.v201710.cm.AdGroupType;
import com.google.api.ads.adwords.axis.v201710.cm.AgeRange;
import com.google.api.ads.adwords.axis.v201710.cm.AgeRangeAgeRangeType;
import com.google.api.ads.adwords.axis.v201710.cm.ApiException;
import com.google.api.ads.adwords.axis.v201710.cm.BiddableAdGroupCriterion;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyConfiguration;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategySource;
import com.google.api.ads.adwords.axis.v201710.cm.BiddingStrategyType;
import com.google.api.ads.adwords.axis.v201710.cm.Campaign;
import com.google.api.ads.adwords.axis.v201710.cm.Criterion;
import com.google.api.ads.adwords.axis.v201710.cm.CriterionType;
import com.google.api.ads.adwords.axis.v201710.cm.CriterionUse;
import com.google.api.ads.adwords.axis.v201710.cm.CriterionUserInterest;
import com.google.api.ads.adwords.axis.v201710.cm.CriterionUserList;
import com.google.api.ads.adwords.axis.v201710.cm.ExplorerAutoOptimizerSetting;
import com.google.api.ads.adwords.axis.v201710.cm.Gender;
import com.google.api.ads.adwords.axis.v201710.cm.GenderGenderType;
import com.google.api.ads.adwords.axis.v201710.cm.Keyword;
import com.google.api.ads.adwords.axis.v201710.cm.KeywordMatchType;
import com.google.api.ads.adwords.axis.v201710.cm.MobileAppCategory;
import com.google.api.ads.adwords.axis.v201710.cm.MobileApplication;
import com.google.api.ads.adwords.axis.v201710.cm.Operator;
import com.google.api.ads.adwords.axis.v201710.cm.Parent;
import com.google.api.ads.adwords.axis.v201710.cm.ParentParentType;
import com.google.api.ads.adwords.axis.v201710.cm.Placement;
import com.google.api.ads.adwords.axis.v201710.cm.Setting;
import com.google.api.ads.adwords.axis.v201710.cm.Vertical;
import com.google.api.ads.adwords.axis.v201710.cm.YouTubeChannel;
import com.google.api.ads.adwords.axis.v201710.cm.YouTubeVideo;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import adword.Connector.Session;
import adword.database.MySingleton;
import adword.interfaces.AppCategories;
import adword.interfaces.Apps;
import adword.interfaces.DisplayCampaign;
import adword.interfaces.Topics;
import adword.interfaces.YoutubeChannels;
import adword.interfaces.YoutubeVideos;
import adword.json.json_encoder;
import adword.json.results;
/**
 * 
 * @author naveen
 *
 */
public class AdgroupAPI extends Campaign{
	
//	public static String updateDisplayAdgroup(Session session,  DisplayCampaign camp) throws Exception {
//		MongoClient client =null;
//		try {
//            results res = new results();
//            List<AdGroup> agroup = updateAdgroup(session,camp);
//            JSONParser parser=new JSONParser();
//		   	Object object = parser.parse(new FileReader("/home/nikhil/workspace/new_test_adword/src/config.json"));
//		   	JSONObject jsonobject=(JSONObject) object;
//		   	String  db_name=(String) jsonobject.get("db_name");
//		   	 client = MySingleton.getInstance();
//		   @SuppressWarnings("deprecation")
//		   	DB db = client.getDB(db_name);  
//            BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
//            for (AdGroup result : agroup) {
////                
//            	camp.setAdGroupId(result.getId());
//            	  List<AdGroupCriterion> bid_keyword = updateBiddableKeyword(session,camp);
//        	  
//                DBCollection adgroup_db= db.getCollection("adwords_adgroups");
//  		        ObjectId id = ObjectId.get();
//  				docBuilder.add("_id", id);
//  				docBuilder.add("adgroup_id", result.getId());
//  				docBuilder.add("name", result.getName());
//  				docBuilder.add("campaign_id", result.getCampaignId());
//  				AdGroupType ag_type = result.getAdGroupType();
//  				docBuilder.add("adgroup_type",ag_type.getValue() );
//  				if (result.getBiddingStrategyConfiguration()!=null){
//  			BiddingStrategyConfiguration bid_config = result.getBiddingStrategyConfiguration();
//  				docBuilder.add("bidding_strategy_id", bid_config.getBiddingStrategyId());
//  				docBuilder.add("bidding_strategy_name",  bid_config.getBiddingStrategyName());
//  				if (bid_config.getBiddingStrategySource()!=null){
//  				BiddingStrategySource b_source = bid_config.getBiddingStrategySource();
//  				docBuilder.add("bidding_strategy_source", b_source.getValue());
//  				}
//  				if (bid_config.getBiddingStrategyType()!=null){
//  				 BiddingStrategyType b_type = bid_config.getBiddingStrategyType();
//  				docBuilder.add("bidding_strategy_type", b_type.getValue());
//  				}
//  				}
//  				AdGroupStatus status = result.getStatus();
//  				docBuilder.add("status", status.getValue());
//  				
//  				DBObject insert_object=docBuilder.get();
//  				adgroup_db.insert(insert_object);
//                res.setSuccess(true);
//                res.setResult(Long.toString(result.getId()));
//            }
//            return json_encoder.javaToJson(res);
//
//        } catch (Exception e) {
//            throw e;
//        }finally{
//        	
//        }
//
//    }
	
	
public static List<AdGroup> updateAdgroup(Session session, DisplayCampaign camp) throws Exception {
	MongoClient client =null;
	System.out.println("In Update adgroup");
	try {
		AdGroupServiceInterface adGroupService
			        = session.services.get(session.session, AdGroupServiceInterface.class);
			List<AdGroup> l1 = new ArrayList<AdGroup>();
			// Create ad group.
			AdGroup adgroup = new AdGroup();
			System.out.println("Adgroup Id is  "+camp.getAdGroupId());
			adgroup.setId(camp.getAdGroupId());
			adgroup.setName(camp.getAdGroupName());
			adgroup.setStatus(AdGroupStatus.ENABLED);
            

			ExplorerAutoOptimizerSetting explorerAutoOptimizerSetting  = new ExplorerAutoOptimizerSetting(); 
          if(camp.getAutomatedTargeting()!=null) {
       	   if(camp.getAutomatedTargeting().equalsIgnoreCase("Aggressive"))
       	   {
       		   explorerAutoOptimizerSetting.setOptIn(true);
       	   }
       	   if(camp.getAutomatedTargeting().equalsIgnoreCase("Conservative"))
       	   {
       		   explorerAutoOptimizerSetting.setOptIn(false);
       	   }
          }
            
			adgroup.setSettings(new Setting[] {explorerAutoOptimizerSetting});

	        adgroup.setCampaignId(camp.getCampaignId());
	       
	        AdGroupOperation operation = new AdGroupOperation();
	        operation.setOperand(adgroup);
	        operation.setOperator(Operator.SET);

	        AdGroupOperation[] operations = new AdGroupOperation[]{operation};
	       
	        System.out.println("ExplorerAutoOptimizerSetting");
	        	        // Add ad groups.
	        AdGroupReturnValue result = adGroupService.mutate(operations);
	        System.out.println("ExplorerAutoOptimizerSetting after");
	        long aid = 0;
	        if(result!=null){
	        	System.out.println(result);
	        	System.out.println("if loop result !=null");
	    		if(result.getPartialFailureErrors()!=null){
	    			System.out.println(result.getPartialFailureErrors().toString());
	    		System.out.println("---->");
	    			throw new ApiException("error", "", result.getPartialFailureErrors());
	    		}
	        for (AdGroup adGroupResult : result.getValue()) {
	            System.out.printf("Ad group with name '%s' and ID %d was updated.%n",
	                    adGroupResult.getName(), adGroupResult.getId());
	           aid = adGroupResult.getId();
	           
	            l1.add(adGroupResult);
	            System.out.println(adGroupResult);
	            JSONParser parser=new JSONParser();
			   	Object object = parser.parse(new FileReader("/home/nikhil/workspace/new_test_adword/src/config.json"));
			   	JSONObject jsonobject=(JSONObject) object;
			   	String  db_name=(String) jsonobject.get("db_name");
			   	 client = MySingleton.getInstance();
			   @SuppressWarnings("deprecation")
			   	DB db = client.getDB(db_name);     
                DBCollection campaign_collection = db.getCollection("adwords_DisplayAdgroups");
                BasicDBObject newDocument = new BasicDBObject();
                
                    try {
                    	
                    	 newDocument.put("localId", camp.getLocalId());
                    	 newDocument.put("adgroup_id", adGroupResult.getId());
                    	 newDocument.put("name", adGroupResult.getName());
                    	 newDocument.put("campaign_id", adGroupResult.getCampaignId());
     	  				AdGroupType ag_type = adGroupResult.getAdGroupType();
     	  				newDocument.put("adgroup_type",ag_type.getValue() );
     	  				if (adGroupResult.getBiddingStrategyConfiguration()!=null){
     	  			BiddingStrategyConfiguration bid_config = adGroupResult.getBiddingStrategyConfiguration();
     	  			newDocument.put("bidding_strategy_id", bid_config.getBiddingStrategyId());
     	  			newDocument.put("bidding_strategy_name",  bid_config.getBiddingStrategyName());
     	  				if (bid_config.getBiddingStrategySource()!=null){
     	  				BiddingStrategySource b_source = bid_config.getBiddingStrategySource();
     	  				newDocument.put("bidding_strategy_source", b_source.getValue());
     	  				}
     	  				if (bid_config.getBiddingStrategyType()!=null){
     	  				 BiddingStrategyType b_type = bid_config.getBiddingStrategyType();
     	  				newDocument.put("bidding_strategy_type", b_type.getValue());
     	  				}
     	  				}
     	  				AdGroupStatus status = adGroupResult.getStatus();
     	  				newDocument.put("status", status.getValue());
     	  				
     	  			  BasicDBObject searchQuery = new BasicDBObject().append("adgroup_id",camp.getAdGroupId());
	    				campaign_collection.update(searchQuery, newDocument);   
     	                
     	            }
     	            

     	        catch (Exception e) {
     	            throw e;
     	        }

                    }
	        }
	        
	        List<AdGroupCriterionOperation> operations1 = new ArrayList<AdGroupCriterionOperation>();
	       
	        CriterionUserInterest cui = new CriterionUserInterest();
	        if(camp.getAudience()!= null){
	        if(camp.getAudience().getAffinity()!=null){
	        	long[] aids = camp.getAudience().getAffinity();
	        	for (int i = 0; i < aids.length; i++) {
	        		cui.setUserInterestId(aids[i]);	
	        		AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
		            AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
		            adGroupCriterion.setAdGroupId(camp.getAdGroupId());;
		            adGroupCriterion.setCriterion(cui);
		            
		            operate.setOperand(adGroupCriterion);
		            operate.setOperator(Operator.SET);
		            operations1.add(operate);
					}
	        }
	        	if(camp.getAudience().getInmarketing()!= null){
	        		long[] iids = camp.getAudience().getInmarketing();
	        		for (int i = 0; i < iids.length; i++) {
					cui.setUserInterestId(iids[i]);	
					AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
		            AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
		            adGroupCriterion.setAdGroupId(camp.getAdGroupId());;
		            adGroupCriterion.setCriterion(cui);
		            
		            operate.setOperand(adGroupCriterion);
		            operate.setOperator(Operator.SET);
		            operations1.add(operate);
					}
	        	}
	        
	        }	
	        System.out.println("----------------1");
	        CriterionUserList cul = new CriterionUserList();
	        if(camp.getAudience().getRemarketing()!=null){
	        	long[] rids = camp.getAudience().getRemarketing();
	        	for (int i = 0; i < rids.length; i++) {
	        		cul.setUserListId(rids[i]);
	        		
	        		AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
		            AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
		            adGroupCriterion.setAdGroupId(camp.getAdGroupId());;
		            adGroupCriterion.setCriterion(cul);
		            
		            operate.setOperand(adGroupCriterion);
		            operate.setOperator(Operator.SET);
		            operations1.add(operate);
				}
	        	
	        	
	        	
	        	
	        }
	      
	        System.out.println("----------------2");
	        Gender g = new Gender();
        	if(camp.getDemographics().getGender()!= null){
        		List<String> g1 = Arrays.asList(camp.getDemographics().getGender());
        	for (int i = 0; i < g1.size(); i++) {
				if (g1.get(i).equalsIgnoreCase("MALE")) 
				{
					g.setGenderType(GenderGenderType.GENDER_MALE);
				}
				if (g1.get(i).equalsIgnoreCase("FEMALE")) 
				{
					g.setGenderType(GenderGenderType.GENDER_FEMALE);
				}
				if (g1.get(i).equalsIgnoreCase("UNKNOWN")) 
				{
					g.setGenderType(GenderGenderType.GENDER_UNDETERMINED);
				}
			}
        	AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
            AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
            adGroupCriterion.setAdGroupId(camp.getAdGroupId());;
            adGroupCriterion.setCriterion(g);
            
            operate.setOperand(adGroupCriterion);
            operate.setOperator(Operator.SET);
            operations1.add(operate);
        	}
        	 System.out.println("----------------3");	
			AgeRange age = new AgeRange();
			if(camp.getDemographics().getAge()!= null)
			{
        		List<String> a1 = Arrays.asList(camp.getDemographics().getAge());
        		for (int i = 0; i < a1.size(); i++) 
        		{
					if(a1.get(i).equals("18_24"))
					{
						age.setAgeRangeType(AgeRangeAgeRangeType.AGE_RANGE_18_24);		
					}
					if(a1.get(i).equals("25_34"))
					{
						age.setAgeRangeType(AgeRangeAgeRangeType.AGE_RANGE_25_34);		
					}
					if(a1.get(i).equals("35_44"))
					{
						age.setAgeRangeType(AgeRangeAgeRangeType.AGE_RANGE_35_44);		
					}
					if(a1.get(i).equals("45_54"))
					{
						age.setAgeRangeType(AgeRangeAgeRangeType.AGE_RANGE_45_54);		
					}
					if(a1.get(i).equals("55_64)"))
					{
						age.setAgeRangeType(AgeRangeAgeRangeType.AGE_RANGE_55_64);		
					}
					if(a1.get(i).equals("65_UP"))
					{
						age.setAgeRangeType(AgeRangeAgeRangeType.AGE_RANGE_65_UP);		
					}
				}
				
        		AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
               AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
               adGroupCriterion.setAdGroupId(camp.getAdGroupId());
               adGroupCriterion.setCriterion(age);
                
                operate.setOperand(adGroupCriterion);
                operate.setOperator(Operator.SET);
                operations1.add(operate);
			}
			 System.out.println("----------------4");
			Parent p = new Parent();
			if(camp.getDemographics().getParentalStatus()!= null){
				List<String> p1 = Arrays.asList(camp.getDemographics().getParentalStatus());
				for (int i = 0; i < p1.size(); i++)
				{
					if(p1.get(i).equalsIgnoreCase("NOT A PARENT")){
						p.setParentType(ParentParentType.PARENT_NOT_A_PARENT);
					}
					if(p1.get(i).equalsIgnoreCase("PARENT")){
						p.setParentType(ParentParentType.PARENT_PARENT);
					}
					if(p1.get(i).equalsIgnoreCase("UNKNOWN")){
						p.setParentType(ParentParentType.PARENT_UNDETERMINED);
					}
				}
				AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
                AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
                adGroupCriterion.setAdGroupId(camp.getAdGroupId());
                adGroupCriterion.setCriterion(p);
                
                operate.setOperand(adGroupCriterion);
                operate.setOperator(Operator.SET);
                operations1.add(operate);
			}
			
			 System.out.println("----------------5");
            MobileAppCategory mac = new MobileAppCategory();
            if(camp.getPlacement().getAppCategories()!=null)
            {
            	if(camp.getPlacement().getAppCategories()!=null) 
            	{
            		AppCategories[] ids = camp.getPlacement().getAppCategories();
            		for (int j = 0; j < ids.length; j++) {
            			mac.setMobileAppCategoryId(ids[j].getMobileAppCategoryId());
            			
            			 AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
	                     AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
	                     adGroupCriterion.setAdGroupId(camp.getAdGroupId());
	                     adGroupCriterion.setCriterion(mac);
	                     
	                     operate.setOperand(adGroupCriterion);
	                     operate.setOperator(Operator.SET);
	                     operations1.add(operate);
						
					}
            	}
            }
            System.out.println("----------------6");
            MobileApplication ma = new MobileApplication();
            if(camp.getPlacement().getApps()!=null)
            	{
            	Apps[] ids = camp.getPlacement().getApps();
            for (int j = 0; j < ids.length; j++) {
				ma.setAppId(ids[j].getAppId());
				System.out.println(ma);
				// ma.setAppId("1-476943146");
				 AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
                 AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
                 adGroupCriterion.setAdGroupId(camp.getAdGroupId());
                 adGroupCriterion.setCriterion(ma);
                 
                 operate.setOperand(adGroupCriterion);
                 operate.setOperator(Operator.SET);
                 operations1.add(operate);
			}
            	
            	
            	}
            
            System.out.println("----------------7");
            YouTubeVideo yv = new YouTubeVideo();
            
            if(camp.getPlacement().getYoutubeVideos()!=null) 
            {
            	System.out.println("inside if");
            	YoutubeVideos[] ids = camp.getPlacement().getYoutubeVideos();
           
            	 for (int j = 0; j < ids.length; j++) {
            		 System.out.println("inside for");
            		 yv.setVideoId(ids[j].getVideoId());
						
            		 AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
                     AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
                     adGroupCriterion.setAdGroupId(camp.getAdGroupId());
                     adGroupCriterion.setCriterion(yv);
                     
                     operate.setOperand(adGroupCriterion);
                     operate.setOperator(Operator.SET);
                     operations1.add(operate);
            	 }
            
            }
            
            System.out.println("----------------8");
            YouTubeChannel yc= new YouTubeChannel();
            if(camp.getPlacement().getYoutubeChannels()!=null)
            {
            	YoutubeChannels[] ids = camp.getPlacement().getYoutubeChannels();
           
            	 for (int j = 0; j < ids.length; j++) {
            		 yc.setChannelId(ids[j].getChannelId());
						
            		 AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
                     AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
                     adGroupCriterion.setAdGroupId(camp.getAdGroupId());
                     adGroupCriterion.setCriterion(yc);
                     
                     operate.setOperand(adGroupCriterion);
                     operate.setOperator(Operator.SET);
                     operations1.add(operate);
            	 }

            }
            System.out.println("----------------9");
         Placement plac = new Placement();
         if(camp.getPlacement().getPlacements()!=null)
         {
        	 String[] urls = camp.getPlacement().getPlacements().getUrl();
        	 for (int j = 0; j < urls.length; j++) {
        		 plac.setUrl(urls[j]);
					
        		 AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
                 AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
                 adGroupCriterion.setAdGroupId(camp.getAdGroupId());
                 adGroupCriterion.setCriterion(plac);
                 
                 operate.setOperand(adGroupCriterion);
                 operate.setOperator(Operator.SET);
                 operations1.add(operate);
        	 }
        	 
         }
            
         System.out.println("----------------10");
         Vertical ver = new Vertical();
         if(camp.getTopics()!=null)
         {
        	 Topics[] topics = camp.getTopics();
        	 for (int i = 0; i < topics.length; i++) {
				ver.setVerticalId(Long.parseLong(topics[i].getVerticalId()));
				ver.setVerticalParentId(Long.parseLong(topics[i].getVerticalParentId()));
				ver.setPath(topics[i].getPath());
				
				 AdGroupCriterionOperation operate = new AdGroupCriterionOperation();
                 AdGroupCriterion adGroupCriterion = new AdGroupCriterion();
                 adGroupCriterion.setAdGroupId(camp.getAdGroupId());
                 adGroupCriterion.setCriterion(ver);
                 
                 operate.setOperand(adGroupCriterion);
                 operate.setOperator(Operator.SET);
                 operations1.add(operate);
			}
         }

              
         
                 

                 
                 
              
         System.out.println("----------------11"); 
			AdGroupCriterionServiceInterface AdGroupCriterionService
            = session.services.get(session.session, AdGroupCriterionServiceInterface.class);
  AdGroupCriterionReturnValue result2 = AdGroupCriterionService.mutate(operations1.toArray(new AdGroupCriterionOperation[operations1.size()]));	
			for(AdGroupCriterion criterionResult:result2.getValue() ){
				System.out.println(criterionResult);
				List<AdGroupCriterion> bid_keyword = updateBiddableKeyword(session,camp);
			}
			System.out.println("AdGroup creation");
			
			try {
				System.out.println("IN Adgroup ad call try ");
             if(camp.getCreatAd().getResponsiveAd()!=null)
             {
            	 System.out.println("IN ADGROUP Responsive display ads");
            	 adword.UpdateDisplay.CreateAdsAPI.updateResponsiveAds(session, camp, camp.getAdId(), camp.getLocalImapgeId());
             }
             if(camp.getCreatAd().getUploadDisplayAds()!=null)
             {
            	 System.out.println("IN createUpload display ads");
            	 adword.UpdateDisplay.CreateAdsAPI.createUploadAd(session, camp, camp.getAdId());
             }
			}catch (Exception e) {
				System.out.println("IN ad group ad call catch ");
				throw e;
			}
		return l1;
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally{
        	
        }

		
	}

public static List<AdGroupCriterion> updateBiddableKeyword(Session session,DisplayCampaign  discamp) throws Exception {
	MongoClient client =null;
	try {
//        // Get the AdGroupService.
  	  AdGroupCriterionServiceInterface adGroupAdService =
  			session.services.get(session.session, AdGroupCriterionServiceInterface.class);

		    // Create a responsive display ad.
		   // AdGroupCriterion ag_criterion = new AdGroupCriterion();
		    
  	   List<AdGroupCriterion> l1=new ArrayList<AdGroupCriterion>();
  	BiddableAdGroupCriterion ag_criterion=new BiddableAdGroupCriterion();
  	 for(int i=0;i<discamp.getKeywords().length;i++){
    	  String[] keyword_input = discamp.getKeywords();
    	// Criterion cr =new Criterion();
    	  int len=keyword_input[i].length()-1;
    	 Keyword keyword=new Keyword();
    	 String key_text;
		 keyword.setMatchType(KeywordMatchType.BROAD);
    	 keyword.setType(CriterionType.KEYWORD);
    	 keyword.setCriterionType("Keyword");
    	  keyword.setText(keyword_input[i]);
     ag_criterion.setCriterion(keyword);
		   ag_criterion.setCriterionUse(CriterionUse.BIDDABLE);
		ag_criterion.setAdGroupId(discamp.getAdGroupId());
//		

		    // Create the operation.
		   AdGroupCriterionOperation adGroupAdOperation1 = new AdGroupCriterionOperation();
		    adGroupAdOperation1.setOperand(ag_criterion);
		    adGroupAdOperation1.setOperator(Operator.SET);
		    // Make the mutate request.
		    AdGroupCriterionReturnValue result =
		        adGroupAdService.mutate(new AdGroupCriterionOperation[] {adGroupAdOperation1});
		    JSONParser parser=new JSONParser();
		   	Object object = parser.parse(new FileReader("/home/nikhil/workspace/new_test_adword/src/config.json"));
		   	JSONObject jsonobject=(JSONObject) object;
		   	String  db_name=(String) jsonobject.get("db_name");
		   	 client = MySingleton.getInstance();
		   @SuppressWarnings("deprecation")
		   	DB db = client.getDB(db_name);        
		    // Display ads.
		    for (AdGroupCriterion keyword_result : result.getValue()) {
		    //	AdGroupCriterion newAd = (AdGroupCriterion) adGroupAdResult.;
		    	System.out.println("keyword result "+keyword_result);
		      System.out.printf(
		          "Responsive display ad with ID  and short headline '%s' was updated.%n",
		        keyword_result.getCriterion());
		    DBCollection bid_cr_collection = db.getCollection("adwords_adgroup_criterion");
		    BasicDBObject newDocument = new BasicDBObject();		   
		    System.out.println("In database keyword");
	  		if(keyword_result!=null){
	  			if(keyword_result.getCriterion()!=null){
	  		Criterion cr = keyword_result.getCriterion();
	  		newDocument.put("criterion_id", cr.getId());
	  		newDocument.put("criteriontype", cr.getCriterionType());
	  	
	  		CriterionType type = cr.getType();
	  		newDocument.put("type", type.getValue());
	  	//docBuilder.add("keyword_name", cr_result.ge);
	  			}
	  		newDocument.put("name", keyword_input[i]);
	  		newDocument.put("ad_group_id", keyword_result.getAdGroupId());
	  		 if(keyword_result.getCriterionUse()!=null){
	  			 CriterionUse cr_use = keyword_result.getCriterionUse();
	  			newDocument.put("criterion_use", cr_use.getValue());
	  	 }
	  		}
	  		BasicDBObject searchQuery = new BasicDBObject().append("ad_group_id",discamp.getAdGroupBid());
	  		bid_cr_collection.update(searchQuery, newDocument); 
             
		  		
		      l1.add(keyword_result);
		      
 
}	   
  	 }
        return l1;
    } catch (Exception e) {
        throw e;
    }
//	finally{
//    	
//    }

}
  
}
