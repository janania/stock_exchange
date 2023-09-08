/**
 * A price comparator for trade orders.
 * 
 * @author Janani Asokumar
 * @version 3/20/2021
 */
public class PriceComparator
    implements java.util.Comparator<TradeOrder>
{

    private boolean asc;

    /**
     * Constructs a price comparator that compares two orders in ascending
     * order. Sets the private boolean ascending flag to true.
     */
    public PriceComparator()
    {
        asc = true;
        

    }


    /**
     * Constructs a price comparator that compares two orders in ascending or
     * descending order. The order of comparison depends on the value of a given
     * parameter.Sets the private boolean ascending flag to asc.
     * 
     * @param asc
     *            - true or false (false = descending)
     */
    public PriceComparator(boolean asc)
    {
        if (asc)
        {
            this.asc = true;
        }
        else
        {
            this.asc = false;
        }
    }


    /**
     * compares two trade orders
     * 
     * @param o1
     *            - the first order
     * @param o2
     *            - the second order
     * @return int - a number based on the types of the order
     */
    public int compare(TradeOrder o1, TradeOrder o2)
    {
        if (o1.isMarket() && o2.isMarket())
        {
            return 0;
        }
        else if (o1.isMarket() && o2.isLimit())
        {
            return -1;
        }
        else if (o1.isLimit() && o2.isMarket())
        {
            return 1;
        }
        else
        {
            if (asc)
            {
                return (int)((o1.getPrice() + .5) * 100) - 
                    (int)((o2.getPrice() + .5) * 100);
            }
            else
            {
                return (int)((o2.getPrice() + .5) * 100) - 
                    (int)((o1.getPrice() + .5) * 100);
            }
        }

    }
    
    /**
     * the toString method for price Comparator
     * @return the string of priceComparator
     */
    public String toString() {
        return "" + asc + "";
    }
    

}
