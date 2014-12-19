import java.util.ArrayList;
import java.util.Collections;

public class Node {
	ArrayList<Integer> sons = null;
	int cntOfDifSon;
	public Node() {
		sons = new ArrayList<Integer>();
	}
	public int getDifSon(){
		return cntOfDifSon;
	}
	public void add(Integer valueOf) {
		// TODO Auto-generated method stub
		sons.add(valueOf);
	}
	
	public void sortNode(){
		Collections.sort(sons);
		cntOfDifSon = 0;
		for(int i = 1; i < sons.size(); i++){
			if(sons.get(i).equals(sons.get(i-1)))continue;
			cntOfDifSon ++;
		}
	}
	
	//二分查找下限
	public int lowerbound(int target) {
		int ret = -1;
		int l = 0, r = sons.size() - 1;
		while(l <= r) {
			int mid = (l+r)/2;
			int midV = sons.get(mid).intValue();
			if(midV == target) {
				l = mid + 1;
				ret = mid;
			}else if(midV < target){
				l = mid+1;
			}else{
				r = mid-1;
			}
		}
		return ret;
	}
	
	//二分查找上限
	public int upperbound(int target) {
		int ret = -1;
		int l = 0, r = sons.size() - 1;
		while( l <= r) {
			int mid = (l+r)/2;
			int midV = sons.get(mid).intValue();
			if(midV == target) {
				r = mid-1;
				ret = mid;
			}else if(midV < target){
				l = mid+1;
			}else{
				r = mid-1;
			}
		}
		return ret;
	}
	
	public int getCnt(int target) {
		int lowb = lowerbound(target);
		int upb = upperbound(target);
		if(lowb == -1) return 0;
		else return upb - lowb + 1;
	}

}