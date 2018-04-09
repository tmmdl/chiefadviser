import java.sql.*;


public class Recipe {

    /**
     *The method is used to return the proper recipe list. Passed raw String parameter is handled
     * to enable full text search. Thereafter pure string parameter is passed to SQL query to select
     * required data.
     *@param rawQuery raw parameter from user
     *@query pure String ready to be passed to SQL query
     */

    public static void get(String rawQuery) { //TODO exception handling by undefined ingredient input

        String query = rawQuery.replaceAll(" ", "&");

        try {
            Connection connection = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/postgres", "postgres","postgres");
            PreparedStatement ps = connection
                    .prepareStatement("" +
                            "select * from cookbase " +
                            "where to_tsvector (ingredients) @@ to_tsquery (?) " +
                            "or to_tsvector(name) @@ to_tsquery (?)");
            ps.setString(1, '%' + query + '%');
            ps.setString(2, '%' + query + '%');
            System.out.println("Executing query");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TalkingBot.contain(rs.getString(2) +
                                "\n\n" + rs.getString(3) +
                                "\n\n" + rs.getString(4) +
                                "\n\n" + rs.getString(8));
            }
        }
        catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }
}
