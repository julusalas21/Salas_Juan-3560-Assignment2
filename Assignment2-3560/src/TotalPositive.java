public class TotalPositive implements Element{
    @Override
    public void accept(AnalyticsVisitor visitor) {
        visitor.visit(this);
    }
}
