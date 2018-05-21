# JMH usage report
#### Microbenchmarking was used to explore which method of Map interface (HashMap realization) takes less time to iterate over all the values
###### Used modes: *all*
###### Used methods:
* keySet (`String key`)
* values (`String value`)
* entrySet (`Map.Entry<> entry`)
#### Method
	@Benchmark
	@BenchmarkMode(Mode.All)
	public String XXX() {
		StringBuilder result = new StringBuilder();
		for (iterator : Iterator) {
		    result.append(iterator);
		}
		return result.toString();
	}
#### Setup
Workstation was not idling during this experiment, but since SSD latency is quite small and CPU has 6C/12T, I supposed it was precise enough.
Map of 100 elements (with 1000 capacity) was filled with `random String -> another random String`.
Then it was saved to file (serialized). In benchmark map is reading back from file to the static field and all the values are concatenating together.
#### Results
| Method        | Throughput, ops/ms    | Throughput, %  | 
| ------------- |:---------------------:| --------------:| 
| keySet        | 414.6±7.0             |       100.0    | 
| values        | 346.3±10.8            |        83.5    | 
| entrySet      | 424.6±8.3             |       102.4    | 