package cn.cloud.domain;

	import java.util.List;
	import java.util.Map;

	public class CloudChart {

		private List<Integer> first;
		private List<Integer> second;
		private List<Integer> sum;
		private List<String> date;
		private Map<Integer,Object> firstMap;
		private Map<Integer,Object> secondMap;
		public CloudChart() {
		}

		public CloudChart(List<Integer> first, List<Integer> second, List<Integer> sum, List<String> date, Map<Integer, Object> firstMap, Map<Integer, Object> secondMap) {
			this.first = first;
			this.second = second;
			this.sum = sum;
			this.date = date;
			this.firstMap = firstMap;
			this.secondMap = secondMap;
		}

		public List<Integer> getFirst() {
			return first;
		}

		public void setFirst(List<Integer> first) {
			this.first = first;
		}

		public List<Integer> getSecond() {
			return second;
		}

		public void setSecond(List<Integer> second) {
			this.second = second;
		}

		public List<Integer> getSum() {
			return sum;
		}

		public void setSum(List<Integer> sum) {
			this.sum = sum;
		}

		public List<String> getDate() {
			return date;
		}

		public void setDate(List<String> datelist) {
			this.date = datelist;
		}

		public Map<Integer, Object> getFirstMap() {
			return firstMap;
		}

		public void setFirstMap(Map<Integer, Object> firstMap) {
			this.firstMap = firstMap;
		}

		public Map<Integer, Object> getSecondMap() {
			return secondMap;
		}

		public void setSecondMap(Map<Integer, Object> secondMap) {
			this.secondMap = secondMap;
		}

		@Override
		public String toString() {
			return "CloudChart{" +
					"first=" + first +
					", second=" + second +
					", sum=" + sum +
					", datelist=" + date +
					", firstMap=" + firstMap +
					", secondMap=" + secondMap +
					'}';
		}
	}
