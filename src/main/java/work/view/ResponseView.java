package work.view;

import java.util.Objects;

public class ResponseView {

    private String result;

    public ResponseView() {
    }

    public ResponseView(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseView that = (ResponseView) o;
        return Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {

        return Objects.hash(result);
    }

    @Override
    public String toString() {
        return "ResponseView{" +
                "result='" + result + '\'' +
                '}';
    }
}
