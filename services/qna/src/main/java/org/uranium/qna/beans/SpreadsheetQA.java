package org.uranium.qna.beans;

/**
 * Specifies how question and answer should be generated for a Google spreadsheet
 */
public class SpreadsheetQA {
    private String sheetName;
    private char questionColumn;
    private char answerColumn;
    private int startColumnIndex;
    private int endColumnIndex;
    private String suffix;
    private String url;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public char getQuestionColumn() {
        return questionColumn;
    }

    public void setQuestionColumn(char questionColumn) {
        this.questionColumn = questionColumn;
    }

    public char getAnswerColumn() {
        return answerColumn;
    }

    public void setAnswerColumn(char answerColumn) {
        this.answerColumn = answerColumn;
    }

    public int getStartColumnIndex() {
        return startColumnIndex;
    }

    public void setStartColumnIndex(int startColumnIndex) {
        this.startColumnIndex = startColumnIndex;
    }

    public int getEndColumnIndex() {
        return endColumnIndex;
    }

    public void setEndColumnIndex(int endColumnIndex) {
        this.endColumnIndex = endColumnIndex;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
