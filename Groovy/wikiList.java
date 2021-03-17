import java.util.regex.Matcher
import java.util.regex.Pattern

class WikiList {

    static def url, con, html,pattern,m,table,row, name, date, state, party

    static void getUrlToText() {
        url = new URL(url)
        con = (HttpURLConnection) url.openConnection()
        con.requestMethod = "GET"
        def br = new BufferedReader(new InputStreamReader(con.getInputStream()))
        html = br.getText()
        // println(html)
        br.close()
         }

    static void main(String[] args) {
        url = "https://en.wikipedia.org/wiki/List_of_members_of_the_18th_Bundestag"
        getUrlToText()
        //get table from text
        getTable()
        getData()
    }

    static void getTable(){

        pattern = Pattern.compile("(?ism)^<table.+>.+</table>\$")
        m = pattern.matcher(html)
        if (m.find()) {
            table =m.group(0)
        }

    }

    static void getData() {
       //get rows
         pattern=Pattern.compile("(?ism)^<tr>.+?</tr>\$")
         m=pattern.matcher(table)

        while(m.find()) {
            row = m.group(0)
            // <td data-sort-value="Aken, Jan van"><span id="A"></span><a href="/wiki/Jan_van_Aken_(politician)" title="Jan van Aken (politician)">Jan van Aken</a></td
            // <td>1968</td>
            //<td>CDU</td>
            //<td>Niedersachsen</td>
            name = Pattern.compile("(?ism)<td.+?>.+?title.+?>(.+?)<\\/a>")
            def nameMatch=name.matcher(row)

            date=Pattern.compile("(?ism)<td>(\\d{4})<\\/td>")
            def dateMatch=date.matcher(row)

            party=Pattern.compile("<td>([A-Z ]+.+?[^a-z])<\/td>")
            def partyMatcher=party.matcher(row)

            state=Pattern.compile("<td>([A-Z].+[a-z])<\\/td>")
            def stateMatcher=state.matcher(row)

            String space=" -- "

            if(nameMatch.find()){
               print(nameMatch.group(1)+space)
            }
            if(dateMatch.find()){
              print(dateMatch.group(1)+space)
            }

            if(partyMatcher.find()){
                print(partyMatcher.group(1)+space)
            }
                if(stateMatcher.find()){
                print(stateMatcher.group(1)+space)
            }
            println()

        }
    }
}
