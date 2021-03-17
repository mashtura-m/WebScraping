package current_scripts

/*
Demo Script 3
Author : Mashtura Mazed
*
* */
import com.rdc.importer.scrapian.ScrapianContext
import com.rdc.importer.scrapian.util.ModuleLoader
import com.rdc.scrape.ScrapeAddress
import com.rdc.scrape.ScrapeEvent
import org.apache.commons.lang.StringUtils

context.setup([connectionTimeout: 20000, socketTimeout: 4000, retryCount: 1, multithread: true])
context.session.encoding = "UTF-8" //change it according to web page's encoding
context.session.escape = true

InvestorAlert ivs=new InvestorAlert(context);
ivs.initParsing();

class InvestorAlert{

    def url="https://market.sec.or.th/public/idisc/en/Viewmore/invalert-head?PublicFlag=Y"
    ScrapianContext context
    def html,name,date,action

    //get data
    void getData() {
//        def tableData=html=~"<table.*?>.+?<\\/table>"
        def rowRegex = html =~ "<tr>.*?<\\/tr>"
           rowRegex.find()
        int count=0;
        while (rowRegex.find()) {
            def row = rowRegex.group(0)
            def nameRegex = row =~ "<tr>\\s*<td.+?>(.*?)<\\/td>"
            def dateRegex = row =~ "(?ism)<td.+?_Center.+?>(\\d{2}\\/\\d{2}\\/\\d{4})"
            def actRegex = row =~ "<td.+?Left.+?>([Us][a-z]+\\s+[a-z]+\\s+.+?)<\\/td>" //https://regex101.com/r/QvFgUc/4

            if (nameRegex.find()) {
                name = nameRegex.group(1)
            }
            if (dateRegex.find()) {
                date = dateRegex.group(1)
            }
            if (actRegex.find()) {
                action = actRegex.group(1).replaceAll(/<.*?>/, ".")
            }
            //System.out.println(name+"\n"+action+"\n"+date)
            createEntity();
            count++

        }
        //println(count)
    }

//
    void createEntity(){
        def entity= context.findEntity([name: name])
        if(!entity){
            entity=context.getSession().newEntity()
            entity.setName(name)
        }

        if(date && action){
            ScrapeEvent se=new ScrapeEvent()
            se.setDescription(date+"  "+action)
            entity.addEvent(se)
        }

    }


//------------------------------------------------------DEFAULTS----------------------------------------------------------//

    InvestorAlert(context){
        this.context=context
    }
    void initParsing(){
        html=invoke(url)
        getData();
    }
    def invoke(url, headersMap = [:], cache = false, tidy = false, miscData = [:]) {
        Map dataMap = [url: url, tidy: tidy, headers: headersMap, cache: cache]
        dataMap.putAll(miscData)
        return context.invoke(dataMap)
    }
}
