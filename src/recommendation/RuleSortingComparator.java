package recommendation;
import java.util.Comparator;
public  class RuleSortingComparator
    implements Comparator<Rule> {
    @Override
    public int compare(Rule r1,
                       Rule r2)
    {

        // Comparing supports
        int SupportCompare = r1.getSupport().compareTo(
            r2.getSupport());

        int ConfCompare = r1.getConf().compareTo(
            r2.getConf());

        // 2nd level confs
        return (SupportCompare == 0) ?((ConfCompare == -1)?1:-1)
                                  : ((SupportCompare==-1)?1:-1);
    }
}
