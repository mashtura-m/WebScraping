/*
Mashtura Mazed
Project : RDC Web Scraping
*/
package current_scripts

import com.rdc.importer.scrapian.ScrapianContext
import com.rdc.importer.scrapian.util.ModuleLoader
import com.rdc.scrape.ScrapeAddress
import com.rdc.scrape.ScrapeEvent
import org.apache.commons.lang.StringUtils

context.setup([connectionTimeout: 20000, socketTimeout: 4000, retryCount: 1, multithread: true])
context.session.encoding = "UTF-8" //change it according to web page's encoding
context.session.escape = true

WikiList1 wiki=new WikiList1(context);
wiki.initParsing();


class WikiList1{

    final ScrapianContext context
    final String mainURL = "https://en.wikipedia.org/wiki/List_of_members_of_the_18th_Bundestag"
    static def  html, table,row, name, party,born,state

    def initParsing() {
        html = invoke(mainURL)
        getData()
    }
    WikiList1(context){
        this.context=context
    }

    void getData(){

        def tr=html=~"(?ism)<tr>.+?<\\/tr>"
        FileWriter fw=new FileWriter(new File("/home/anika/Desktop/wikiList.txt"))
        //println(tr.find())


        while(tr.find()){

            def row=tr.group(0)
            name=row=~"(?ism)<td.*?title.+?>(.+?)<\\/a>"
            party=row=~"td>([A-Z ]+.+?[^a-z])<\/td>"
            born=row=~"(?ism)<td>(\\d{4})<\\/td>"
            state=row=~"(?m)<td>([A-Z ].+[a-z]+)<\\/td>"

            String info="";

            if(name.find()){
                info+=(name.group(1))
            }
            info+="\n"
            if(born.find()){
                info+=born.group(1)
            }
            info+="\n"
            if(party.find()){
                info+=party.group(1)

            }
            info+="\n"
            if(state.find()){
                info+=state.group(1)
            }
            info+="\n"
            //System.out.println(info)
            fw.write(info+"\n\n")
        }
   fw.close()

   }

    def invoke(url, headersMap = [:], cache = false, tidy = false, miscData = [:]) {
        Map dataMap = [url: url, tidy: tidy, headers: headersMap, cache: cache]
        dataMap.putAll(miscData)
        return context.invoke(dataMap)
    }
}
