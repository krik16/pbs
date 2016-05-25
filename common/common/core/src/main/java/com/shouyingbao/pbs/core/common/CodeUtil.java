package com.shouyingbao.pbs.core.common;

public class CodeUtil {

	/**
	 * 首先，Math.random()取值范围是[0,1)
	 * 那么Math.random()*9000的取值范围是[0,9000);
	 * 那么Math.random()*9000+1000的取值范围是[1000,10000)。
	 * 注：中括号表示可以取到，而小括号表示不能取到！
	 * @return
	 */
	public static String bornCode() {
		String code = String.valueOf((int)((Math.random()*9+1)*100000));
		if(code.length() == 6) {
			return code;
		}else {
			return bornCode();
		}
	}
	
	/**
	 * 取平均每天的分配
	 * @param allAmount
	 * @param dailyNum
	 * @return
	 */
	public static long[] getEveryDailyAmount(long allAmount, int dailyNum) {
		if(allAmount>0 && dailyNum>0) {
			long baseEveryDailyAmount =  allAmount / dailyNum;//某一个奖的总数量
			int leaveNum = (int)allAmount % dailyNum;//平均每天的数量
			long[] num = new long[dailyNum];
			if(leaveNum != 0) {
				for(int i=0; i<dailyNum; i++) {
					long e = baseEveryDailyAmount;
					if(leaveNum > 0) {
						e++;
					}
					leaveNum--;
//					System.out.println(i+";"+e);
					num[i] = e;
				}
			}else {
				for(int i=0; i<dailyNum; i++) {
					num[i] = baseEveryDailyAmount;
				}
			}
			return num;
		}else {
			return null;
		}
	}
	
	public static void main(String[] args) {
		/*for(int i=0; i<5; i++) {
			System.out.print(randomIntSmallThanParam(5)+",");
		}*/
		long[] q = getEveryDailyAmount(1, 3);
		for(int i=0; i<3; i++) {
			System.out.println(q[i]);
		}
		/*int a = 65;//某一个奖的总数量
		int b = 3;//平均分成三天
		int c = a / b;
		int d = a % b;
		if(d != 0) {
			for(int i=0; i<b; i++) {
				int e = c;
				if(d > 0) {
					e++;
				}
				d--;
				System.out.println(i+";"+e);
			}
		}else {
			//每天c个
			System.out.println(c);
		}*/
	}
	
	/**
	 * 生成一个小于max的随机整数
	 * @param max
	 * @return
	 */
	public static int randomIntSmallThanParam(int max) {
		int i = (int)((Math.random()*max));
		if(i < max) {
			return i;
		}else {
			return randomIntSmallThanParam(max);
		}
	}
}
