public class TotalUsers implements Element{

    @Override
    public void accept(AnalyticsVisitor visitor) {
        visitor.visit(this);
    }
}
