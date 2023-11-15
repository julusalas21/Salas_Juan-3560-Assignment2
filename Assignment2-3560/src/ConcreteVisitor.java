//this concrete visitor provides total analytics for each type of data
public class ConcreteVisitor implements AnalyticsVisitor{
    @Override
    public String visit(TotalUsers users) {
        return "Total Users: "+Integer.toString(AdminControlPanel.getInstance().getUsers().size());
    }

    @Override
    public String visit(TotalMessages messages) {
        return "Total messages: "+Integer.toString(UserViewPanel.getTotalMessages());
    }

    //plus 1 is added to include root group
    @Override
    public String visit(TotalGroups groups) {
        return "Total Groups: "+Integer.toString(AdminControlPanel.getInstance().getGroups().size()+1);
    }

    @Override
    public String visit(TotalPositive poitives) {
        return "Percentage of Positive tweets: "+Double.toString(UserViewPanel.getPositivePercentage())+("%");
    }
}
