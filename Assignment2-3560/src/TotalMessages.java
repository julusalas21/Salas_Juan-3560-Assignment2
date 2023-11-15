public class TotalMessages implements Element{
    @Override
    public void accept(AnalyticsVisitor visitor) {
        visitor.visit(this);
    }
}
