package win.night;

/**
 * Created by Administrator on 2017/6/3.
 *
 * 题目描述:
 * 在股市的交易日中,假设最多可进行两次买卖(即买和卖的次数均小于等于2),
 * 规则是必须一笔成交后进行另一笔(即买-卖-买-卖的顺序进行)。
 * 给出一天中的股票变化序列,请写一个程序计算一天可以获得的最大收益。
 * 请采用实践复杂度低的方法实现。
 * 给定价格序列prices及它的长度n,请返回最大收益。保证长度小于等于500。
 *
 * 测试样例：
 * [10,22,5,75,65,80],6
 *
 * 返回：
 * 87
 *
 * 参考链接:http://blog.csdn.net/u012351768/article/details/51577272
 */
public class StockTradingDay
{
    public static void main(String[] args)
    {
        int stockDatas[] = {10, 22, 5, 75, 65, 80};

        int profit = maxProfit(stockDatas, 6);
        System.out.println("maxProfit: " + profit);

        int maxProfit = maxProfit2(stockDatas, 6);
        System.out.println("maxProfit2: " + maxProfit);
    }

    /**
     * 求最大收益,交易次数小于等于两次
     *
     * @param prices 每一天的股票价格
     * @param n      天数
     * @return 返回小于等于两次交易所获取的最大利润
     */
    public static int maxProfit(int[] prices, int n)
    {
        int[][] profit = new int[n][n];
        int max = Integer.MIN_VALUE;
        //对角线无效,对角线以下的三角形无效
        //记录一次交易所获取的利润
        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                profit[i][j] = prices[j] - prices[i];
                //保存一次交易的最大值
                max = Math.max(profit[i][j], max);
            }
        }

        //遍历数组,计算两次交易的利润最大值
        for (int i = 0; i < n; i++)
        {
            for (int j = i + 1; j < n; j++)
            {
                //只有当一次交易的利润是正的的时候,才会考虑计算第二次利润
                if (profit[i][j] > 0)
                {
                    //在后续的日期里计算第二次交易的最大值
                    for (int p = j + 1; p < n; p++)
                    {
                        //第i天卖出之后,第i天可以接着买入,所以这里用j+1而不是j+2
                        for (int q = j + 1; q < n; q++)
                        {
                            //负利润被忽略
                            if (profit[p][q] < 0)
                                continue;
                            else
                            {
                                //计算两次交易的利润和
                                int sum = profit[i][j] + profit[p][q];
                                //与最大利润进行比较
                                max = Math.max(sum, max);
                            }
                        }
                    }
                }
            }
        }
        return max;
    }

    /**
     * 求最大收益，交易次数小于等于两次
     *
     * @param prices 每一天的股票价格
     * @param n      天数
     * @return 返回小于等于两次交易所获取的最大利润
     */
    public static int maxProfit2(int[] prices, int n)
    {
        //第i天之前的最大利益
        int[] preProfit = new int[n];
        //第i天之后的最大
        int[] postProfit = new int[n];
        //总的最大利润
        int max = Integer.MIN_VALUE;

        //如果今天的价格减掉最小价格比截止到昨天的最大收益大，就用今天的价格减去最小价格，否则，用截止到昨天的最大收益
        int minBuy = prices[0];
        for (int i = 1; i < n; i++)
        {
            minBuy = Math.min(minBuy, prices[i]);
            preProfit[i] = Math.max(preProfit[i - 1], prices[i] - minBuy);
        }
        //如果最大价格减掉今天价格比明天以后买入的最大收益大，就用最大价格减掉今天价格，否则，用明天以后买入的最大收益
        int maxSell = prices[n - 1];
        for (int i = n - 2; i >= 0; i--)
        {
            maxSell = Math.max(maxSell, prices[i]);
            postProfit[i] = Math.max(postProfit[i + 1], maxSell - prices[i]);
        }
        //求出两次交易的和，与总的最大利润进行比较
        for (int i = 0; i < n; i++)
        {
            max = Math.max(preProfit[i] + postProfit[i], max);
        }
        return max;
    }
}
