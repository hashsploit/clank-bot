package net.hashsploit.mediusdiscordbot.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;

import org.json.JSONObject;

import net.hashsploit.mediusdiscordbot.proto.MediusStructures.TableRow;
import net.hashsploit.mediusdiscordbot.proto.MediusStructures.TableData;

public class PagingUtil{
    public static int calculateMaxPage(int entriesPerPage, int entriesCount){
        int maxPage = (int) Math.floor(entriesCount/entriesPerPage);
        if (entriesCount % entriesPerPage == 0) maxPage = Math.max(maxPage - 1, 0);   //base case if we have ENTRIES_PER_PAGE divides players.size()

        return maxPage;
    }

    public static ArrayList<ArrayList<String>> getTableDataRowsRanged(TableData tdObj, int entriesPerPage, int selectedPage, Comparator<ArrayList<String>> customComparator){
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        ArrayList<TableRow> rows = new ArrayList<TableRow>(tdObj.getRowsList());

        int lowerBound = entriesPerPage*(selectedPage);
        int upperBound = Math.min((entriesPerPage)*(selectedPage+1), rows.size());

        for(int i = 0; i < rows.size(); ++i){
            ArrayList<String> nextRow = new ArrayList<String>();
            for (String val : rows.get(i).getValuesList()){
                nextRow.add(val);
            }
            res.add(nextRow);
        }

        Collections.sort(res, customComparator);
        return new ArrayList<ArrayList<String>>(res.subList(lowerBound, upperBound));
    }
    
    public static String createHeadSection(String textColor){
        ArrayList<String> lines = new ArrayList<>(Arrays.asList(
            "<head>",
                "<style>",
                    "body{",
                        "background-color: white;",
                        "font-size: 50px;",
                    "}",
                    "table {",
                        "font-family: calibri, Arial, Helvetica, sans-serif;",
                        "border-collapse: collapse;",
                        "width: 700px;",
                        "background-color: black;",
                        "color: white;",
                    "th {",
                        "padding-top: 10px;",
                        "padding-bottom: 10px;",
                        "text-align: left;",
                        "background-color: #000000;",
                        "font-weight: bold;",
                        // "border-bottom: 1px rgb(255, 217, 0) solid;",
                    "}",
                "</style>",
            "</head>"
        ));

        StringBuilder html = new StringBuilder();
        for(String line : lines){
            html.append(line);
        }

        return html.toString();
    }

    private static String createTableHeadersAndValues(HashMap<String, String> colors, int colToColorBy, ArrayList<String> headers, ArrayList<ArrayList<String>> tableData){
        StringBuilder headerHtml = new StringBuilder();
        
        // headerHtml.append("<tr>");
        // for (String header : headers){
        //     headerHtml.append(String.format("<th>%s</th>", header));
        // }
        // headerHtml.append("</tr>");

        StringBuilder dataHtml = new StringBuilder();

        for (ArrayList<String> row : tableData){
            dataHtml.append("<tr>");
            for (String dataVal : row){
                dataHtml.append(String.format("<td style = \"color: %s; padding:0 12px 0 25px;\">%s</td>", colors.get(row.get(colToColorBy)), dataVal));
            }
            dataHtml.append("</tr>");
        }

        return headerHtml.toString() + dataHtml.toString();
    }

    public static String createTableSection(HashMap<String, String> colors, int colToColorBy, ArrayList<String> headers, ArrayList<ArrayList<String>> tableData){
        return String.format("<table>%s</table>", createTableHeadersAndValues(colors, colToColorBy, headers, tableData));
    }

    public static String createHTMLTable(String headSection, String tableSection){
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>")
        .append("<html>")
        .append(headSection)
        .append(tableSection)
        .append("</html>");

        return html.toString();
    }
}