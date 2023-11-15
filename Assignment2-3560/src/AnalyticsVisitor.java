//utilizes the visitor design pattern in order to gather data
//will grab number of users, messages, groups, and positive words
public interface AnalyticsVisitor {
    String visit(TotalUsers users);
    String visit(TotalMessages messages);
    String visit(TotalGroups groups);
    String visit(TotalPositive poitives);
}
