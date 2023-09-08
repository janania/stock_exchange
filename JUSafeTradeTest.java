import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

/**
 * SafeTrade tests:
 *   TradeOrder
 *   PriceComparator
 *   Trader
 *   Brokerage
 *   StockExchange
 *   Stock
 *
 * @author Janani Asokumar
 * @version 3/21/2021
 * @author Assignment: JM Chapter 19 - SafeTrade
 * 
 * @author Sources: TODO sources
 *
 */
public class JUSafeTradeTest
{
    // --Test TradeOrder
    /**
     * TradeOrder tests:
     *   TradeOrderConstructor - constructs TradeOrder and then compare toString
     *   TradeOrderGetTrader - compares value returned to constructed value
     *   TradeOrderGetSymbol - compares value returned to constructed value
     *   TradeOrderIsBuy - compares value returned to constructed value
     *   TradeOrderIsSell - compares value returned to constructed value
     *   TradeOrderIsMarket - compares value returned to constructed value
     *   TradeOrderIsLimit - compares value returned to constructed value
     *   TradeOrderGetShares - compares value returned to constructed value
     *   TradeOrderGetPrice - compares value returned to constructed value
     *   TradeOrderSubtractShares - subtracts known value & compares result
     *     returned by getShares to expected value
     */
    private String symbol = "GGGL";
    private boolean buyOrder = true;
    private boolean marketOrder = true;
    private int numShares = 123;
    private int numToSubtract = 24;
    private double price = 123.45;

    @Test
    public void tradeOrderConstructor()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        String toStr = to.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
                    toStr.contains( "TradeOrder[Trader trader:null" )
                        && toStr.contains( "java.lang.String symbol:" + symbol )
                        && toStr.contains( "boolean buyOrder:" + buyOrder )
                        && toStr.contains( "boolean marketOrder:" + marketOrder )
                        && toStr.contains( "int numShares:" + numShares )
                        && toStr.contains( "double price:" + price ) );
    }
    
    @Test
    public void TradeOrderToString()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNotNull( to.toString() );
    }

    @Test
    public void tradeOrderGetTrader()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNull( "<< TradeOrder: " + to.getTrader() + " should be null >>",
                    to.getTrader() );
    }

    @Test
    public void tradeOrderGetSymbol()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getTrader() + " should be "
            + symbol + " >>", symbol, to.getSymbol() );
    }

    @Test
    public void tradeOrderIsBuy()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertTrue( "<< TradeOrder: " + to.isBuy() + " should be " + buyOrder
            + " >>", to.isBuy() );
    }

    @Test
    public void tradeOrderIsSell()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertFalse( "<< TradeOrder: " + to.isSell() + " should be "
            + !buyOrder + " >>", to.isSell() );
    }

    @Test
    public void tradeOrderIsMarket()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.isMarket() + " should be "
            + marketOrder + " >>", to.isMarket() );
    }

    @Test
    public void tradeOrderIsLimit()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertFalse( "<< TradeOrder: " + to.isLimit() + " should be "
            + !marketOrder + ">>", to.isLimit() );
    }

    @Test
    public void tradeOrderGetShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.getShares() + " should be "
            + numShares + ">>", numShares == to.getShares()
            || ( numShares - numToSubtract ) == to.getShares() );
    }

    @Test
    public void tradeOrderGetPrice()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getPrice() + " should be " + price
            + ">>", price, to.getPrice(), 0.0 );
    }

    @Test
    public void tradeOrderSubtractShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        to.subtractShares( numToSubtract );
        assertEquals( "<< TradeOrder: subtractShares(" + numToSubtract
            + ") should be " + ( numShares - numToSubtract ) + ">>", numShares
            - numToSubtract, to.getShares() );
    }
    
    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
    }

    @Test
    public void traderWindowShowMessage()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
        tw.showMessage( null );
    }

    //  --Test PriceComparator
    
    @Test 
    public void priceComparatorToString() {
        PriceComparator p = new PriceComparator();
        assertNotNull(p.toString());
    }
   
   @Test 
   public void priceComparatorConstructor() {
       PriceComparator p = new PriceComparator();
       assertEquals("<< Invalid priceComparator Constructor >>", p.toString(), "true");
   }
   
   @Test
   public void priceComparatorConstructorWithParam() {
       PriceComparator p = new PriceComparator(false);
       assertEquals("<< Invalid priceComparator Constructor >>", p.toString(), "false");
   }
   
   @Test
   public void priceComparatorCompare() {
       PriceComparator p = new PriceComparator();
       PriceComparator p2 = new PriceComparator(false);
       double price2 = 357.89;
       TradeOrder o1 = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price);
       TradeOrder o2 = new TradeOrder(null, symbol, buyOrder, marketOrder, numShares, price2);
       TradeOrder o3 = new TradeOrder(null, symbol, buyOrder, false, numShares, price);
       TradeOrder o4 = new TradeOrder(null, symbol, buyOrder, false, numShares, price2);
       
       assertEquals("<< PriceComparator: compare(market, market) should be 0 >>", 
           p.compare(o1,  o2), 0 );
       assertEquals("<< PriceComparator: compare(market, limit) should be -1 >>", 
           p.compare(o1, o3), -1);
       assertEquals("<< PriceComparator: compare(limit, market) should be 1 >>", 
           p.compare(o3, o1), 1);
       
       int pAsc = ((int)((o3.getPrice() + .5) * 100) - 
           (int)((o4.getPrice() + .5) * 100));
       assertEquals("<< PriceComparator: compare(limit, limit) should be "+  pAsc +") >>", 
           p.compare(o3, o4), pAsc);
       
       int pDesc = ((int)((o4.getPrice() + .5) * 100) - 
           (int)((o3.getPrice() + .5) * 100));
       
       assertEquals("<< PriceComparator(desc): compare(limit, limit) should be "+  pDesc +") >>", 
           p2.compare(o3, o4), pDesc);  
   }
    // --Test Trader
   StockExchange exchange = new StockExchange();
   
   Brokerage b = new Brokerage(exchange);
   
   Trader t = new Trader(b, "Bob", "123");
   Trader t2 = new Trader(b, "John", "567");
   Trader t3 = new Trader(b, "bob", "8910");
   
   String s = t.toString();
    @Test
    public void traderConstructor() {
        assertTrue("<< Invalid Trader Constructor >>", 
            s.contains("Trader[Brokerage brokerage") &&
            s.contains("java.lang.String screenName:Bob") &&
            s.contains("java.lang.String password:123") &&
            s.contains("TraderWindow myWindow:null") &&
            s.contains("java.util.Queue mailbox:[]"));
    }
    
    @Test
    public void traderCompareTo() {
        
        assertEquals("<< Trader: Trader Bob compared To Trader John should be " + 
        t.getName().compareToIgnoreCase(t2.getName()) + " >>", 
        t.getName().compareToIgnoreCase(t2.getName()), 
        t.compareTo(t2));
    }
    
    @Test
    public void traderEquals() {
        
        assertTrue("<< Trader: Trader Bob equals() Trader bob should be True >>", 
        t.equals(t3));
        
        assertFalse("<< Trader: Trader Bob equals() Trader John should be False >>",
        t.equals(t2));
        
        String exceptionTest = "Janani";
        try {
            t.equals(exceptionTest);
        }
        catch (ClassCastException exception){
            assertFalse("<< Trader: equals() only compares two traders >>", false);
        }
    }
    
    @Test
    public void TraderGetName() {
        assertEquals("<< Trader: getName() should be Bob >>", "Bob", t.getName() );
    }
    
    @Test
    public void TraderGetPassWord() {
        assertEquals("<< Trader: getPassword() should be 123  >>", "123", t.getPassword());
    }
    
    @Test
    public void TraderGetQuote() {
        exchange.listStock("GGGL", "Giggle.com", 10.00);
        t.getQuote(symbol);
        assertTrue("<< Invalid trader getQuote >>", t.hasMessages());
        
    }
    
    @Test 
    public void TraderHasMessages() {
        exchange.listStock("GGGL", "Giggle.com", 10.00);
        assertFalse("<< Trader: hasMessages should be false", t.hasMessages());
        t.receiveMessage("testing 123");
        assertTrue("<<Trader: hasMessages should be true>>", t.hasMessages());
    }
    
    @Test
    public void TraderOpenWindow() {
        t.receiveMessage("Testing 123");
        t.openWindow();
        assertFalse("<< Trader: window shouldn't be null >>", t.hasMessages());
    }
    
    @Test
    public void TraderPlaceOrder() {
        TradeOrder to = new TradeOrder(t, symbol, buyOrder,marketOrder, numShares, price);
        t.placeOrder(to);
        assertTrue("<< Trader: placeOrder is invalid >>", t.hasMessages());
    }
    
    @Test
    public void TraderQuit() {
        //assertTrue("<<Trader: Trader quit isn't working >>", b.getLoggedTraders().contains(t));
        t.quit();
        assertFalse("<<Trader: Trader quit isn't working >>", b.getLoggedTraders().contains(t));
    }
    
    @Test
    public void TraderReceiveMessages() {
        t.receiveMessage("testing 123");
        assertTrue("<< Trader: receiveMessages shouldn't do anything when window isn't open >>"
            , t.hasMessages());
        
        t.openWindow();
        
        assertFalse("<< Trader: receiveMessages should empty mailbox >>", 
            t.hasMessages());
    }
    
    @Test
    public void TraderToString() {
        assertNotNull(t.toString());
    }
   
    // --Test Brokerage
    
    @Test
    public void BrokerageConstructor() {
        StockExchange e = new StockExchange();
        Brokerage brok = new Brokerage(e);
        String s = brok.toString();
        //System.out.println(s);
        assertTrue("<< Invalid BrokerageContructor >>",
            s.contains("Brokerage"));
//            s.contains("loggedTraders:[]") &&
//            s.contains("exchange:StockExchange[java.util.Map listedStocks:{}]" ));
    }
    
    @Test
    public void BrokerageAddUser() {
        
        assertEquals("<< Brokerage: name should be greater than 4 and less than 10 >>",
            -1, b.addUser("k", "123"));
        assertEquals("<< Brokerage: password should be greater than 2 and less than 10 >>",
            -2, b.addUser("Janani", "1"));
        
        b.addUser("Janani", "123");
        assertEquals("<< Brokerage: trader already in systerm should return -3 >>",
            -3, b.addUser("Janani","123" ));
        
        assertTrue("<< Brokerage: trader should be added to traders map >>",
            b.getTraders().containsKey("Janani"));
        
    }
    
    @Test
    public void BrokerageGetQuote() {
        b.getQuote("GGGL", t);
        assertTrue("<< Invalid Brokerage getQuote >>",
            t.hasMessages());
    }
    
    @Test
    public void BrokerageLogin() {
        assertEquals("<< Trader: trader not registrated should return -1 >>",
            -1, b.login("Janani", "123"));
        
        b.addUser("Janani", "123");
        assertEquals("<< Trader: wrong password should return -2", -2, 
            b.login("Janani", "567") );
        
        b.login("Janani", "123");
        assertEquals("<< Trader: trader already logged in should return -3",
            -3, b.login("Janani", "123"));
        
        b.addUser("Jana", "897");
        b.login("Jana", "897");
        
        assertTrue("<< Invalid Brokerage login >>", 
            b.getLoggedTraders().contains(b.getTraders().get("Jana")));
    }
    
    @Test
    public void BrokerageLogout() {
        b.addUser("Janani", "123");
        b.login("Janani", "123");
        b.logout(b.getTraders().get("Janani"));
        assertFalse("<< Invalid Brokerage logout >>",
            b.getLoggedTraders().contains(b.getTraders().get("Janani")));
    }
    
    @Test
    public void BrokeragePlaceOrder() {
        
        TradeOrder to = new TradeOrder( t, symbol, buyOrder, marketOrder,
            numShares, price );
        assertFalse("<< Invalid Brokerage place order >>", t.hasMessages());
        b.placeOrder(to);
        assertTrue("<< Invalid Brokerage placeorder >>",
            t.hasMessages());
    }
    
    @Test
    public void BrokerageToString() {
        String s = b.toString();
        assertNotNull(s);
        
    }
    
    // --Test StockExchange
    
    @Test
    public void StockExchangeConstructor() {
        assertTrue("<< Invalid StockExchange Constructor >>", 
            exchange.getListedStocks() != null);
    }
    
    @Test
    public void StockExchangeGetQuote() {
        assertEquals("<< StockExchange: should return symbol not found message >>",
            exchange.getQuote("Jan"), "Jan not found.");
        
        exchange.listStock("GGGL", "Giggle.com", 10.00);
        assertTrue(" StockExhange: getQuote invalid >>",
            exchange.getQuote("GGGL") != "");
    }
    
    @Test
    public void StockExchangelistStock() {
        exchange.listStock("Jan", "Janani.com", 10.00);
        assertTrue("StockExchange: listStock invalid", 
            exchange.getListedStocks().containsKey("Jan"));  
    }
    
    @Test
    public void StockExchangePlaceOrder() {
        TradeOrder to = new TradeOrder( t, symbol, buyOrder, marketOrder,
            numShares, price );
        exchange.placeOrder(to);
        assertTrue("<< StockExchange: Invalid PlaceOrder 1>>",
            t.mailbox().peek().contains("not found"));
        
        exchange.listStock(symbol, "Giggle.com", price);
        exchange.placeOrder(to);

        assertFalse("<< StockExchange: Invalid PlaceOrder 2>>",
            t.mailbox().contains("New order:"));
    }
    
    @Test
    public void StockExchangetoString() {
        assertNotNull(exchange.toString());
        
    }
    
    // --Test Stock
    
    Stock stock = new Stock("GGGL", "Giggle.com", 10);
    
    @Test
    public void StockConstructor() {
        s = stock.toString();
        assertTrue("<< invalid Stock Constructor >>",
            s.contains("stockSymbol:GGGL") &&
            s.contains("companyName:Giggle.com") &&
            s.contains("loPrice:10.0") &&
            s.contains("hiPrice:10.0") &&
            s.contains("lastPrice:10.0") &&
            s.contains("volume:0") &&
            s.contains("buyOrders:[]") &&
            s.contains("sellOrders:[]"));
    }
    
    @Test
    public void StockGetQuote() {
        String stockQuote = stock.getQuote();
        assertTrue("<< Invalid Stock getQuote >>",
            stockQuote.contains("Giggle.com (GGGL)") &&
            stockQuote.contains("Price: 10.00") &&
            stockQuote.contains("hi: 10.00") &&
            stockQuote.contains("lo: 10.00") &&
            stockQuote.contains("vol: 0") &&
            stockQuote.contains("Ask: none") &&
            stockQuote.contains("Bid: none"));
    }
    
    @Test
    public void StockPlaceOrder() {
        TradeOrder to = new TradeOrder( t, symbol, buyOrder, marketOrder,
            numShares, price );
        stock.placeOrder(to);
        System.out.println(t.mailbox().peek());
        String m = t.mailbox().peek();
        assertTrue("<< Invalid Stock PlaceOrder >>",
            m.contains("New order: ") &&
            m.contains("Buy GGGL (Giggle.com)") &&
            m.contains("123 shares at market"));
    }
    
    @Test
    public void StockExecuteOrder() {
        TradeOrder to = new TradeOrder( t, symbol, buyOrder, marketOrder,
            numShares, price );
        TradeOrder to2 = new TradeOrder( t2, symbol, false, marketOrder, numShares, price);
        stock.placeOrder(to);
        stock.placeOrder(to2);
        
        t.mailbox().remove();
        t2.mailbox().remove();
        
        assertTrue("<< invalid Stock Execute order >>",
            t.mailbox().peek().contains("bought"));
        
        assertTrue("<< invalid Stock Execute order >> 2",
            t2.mailbox().peek().contains("sold"));
        
        t.mailbox().remove();
        t2.mailbox().remove();
        
        TradeOrder to3 = new TradeOrder( t, symbol, buyOrder, false,
            numShares, price - 10 );
        TradeOrder to4 = new TradeOrder( t2, symbol, false, false, numShares, price);
        
        stock.placeOrder(to3);
        stock.placeOrder(to4);
        
        t.mailbox().remove();
        t2.mailbox().remove();
        
        assertTrue("<< invalid Stock Execute order 3 >>",
            t.mailbox().isEmpty());
        assertTrue("<< invalid Stock Execute order 4 >>",
            t2.mailbox().isEmpty());
        
        
        TradeOrder to6 = new TradeOrder( t2, symbol, buyOrder, false, numShares + 100, price);
        stock.placeOrder(to6);
        
        assertEquals("<< invalid Stock Execture order >>",
            to6.getShares(), 100);
        
        
    }
    
    @Test
    public void StockPlacetoString() {
        assertNotNull(s);
    }
    
    @Test
    public void StockGetBuyOrders() {
        assertNotNull(stock.getBuyOrders());
    }
    
    @Test
    public void StockGetSellOrders() {
        assertNotNull(stock.getSellOrders());
    }

    
    // Remove block comment below to run JUnit test in console
/*
    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUSafeTradeTest.class );
    }
    
    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "JUSafeTradeTest" );
    }
*/
}

