import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Parse { //TODO method calculating difficulty score of each recipe


    /**
     *This method is used to parse required data from website metbexdenxeberler.com
     *and set the data to predefined database. To parse the site Jsoup library was used.
     */

    public static void parse() {

        int i = 1;
        Connection connection;
        for (; i <=22; i++) {
            try {
                Document document =
                        Jsoup.connect("http://metbexdenxeberler.com/category/%C9%99sas-yem%C9%99kl%C9%99r/page/()/"
                                .replace("()", i + ""))
                                .get();
                for (Element element : document.select(".title a[href]")) {
                    String source = element.attr("href");
                    String id = source.substring(source.indexOf("com/") + 4); //unique id of each recipe
                    try {
                        document = Jsoup.connect(source).get();
                        for (Element element2 : document.select(".hrecipe")) {
                            String title = element2.select(".title").text();
                            String ingredients = element2.select(".shortcode-ingredients").text();
                            Elements count1 = document.select(".shortcode-ingredients");
                            Elements ingSize = count1.select("li");
                            int ing = ingSize.size();
                            Elements count2 = document.select(".shortcode-directions");
                            Elements insSize = count2.select("li");
                            int ins = insSize.size();
                            String instruction = element2.select(".shortcode-directions").text();
                            try {
                                connection = DriverManager.getConnection(
                                        "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres","postgres");
                                PreparedStatement ps = connection.prepareStatement(
                                        "insert into chiefbase values(?, ?, ?, ?, ?, ?, ?, ?)");
                                ps.setString(1, id);
                                ps.setString(2, title);
                                ps.setString(3, ingredients);
                                ps.setString(4, instruction);
                                ps.setInt(5, ing);
                                ps.setInt(6, ins);
                                ps.setInt(7,ing + ins);
                                ps.setString(8, source);
                                ps.executeUpdate();
                                System.out.println("done");
                            }
                            catch (SQLException sqle) {
                                System.out.println(sqle);
                            }
                        }
                    }
                    catch (IOException ioe) {
                        System.out.println(ioe);
                    }
                }
            }
            catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
    }
}