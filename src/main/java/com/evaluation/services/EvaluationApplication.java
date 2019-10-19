package com.evaluation.services;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EvaluationApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(EvaluationApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(EvaluationApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
	public void run(String... args) {

		for (int i = 0; i < args.length; ++i) {
			LOG.info("args[{}]: {}", i, args[i]);
		}

		Integer answer = calculateBoxPiles(Integer.valueOf(args[0]), Integer.valueOf(args[1]),
				Integer.valueOf(args[2]));
		LOG.info("The answer is: " + answer);
	}

	public Integer calculateBoxPiles(Integer n, Integer m, Integer p) {
		if (n <= m) {
			return n;
		}
		// List<Integer> tempList = new ArrayList<Integer>();
		Map<Integer, Integer> count = new HashMap<>();
		EvalObj evalObj = new EvalObj(n);
		Integer totalP = 0;

		Deque<EvalObj> deque = new LinkedList<>();
		deque.add(evalObj);

		while (!deque.isEmpty()) {
			if (deque.peek().getValue() <= m) {
				if (deque.peek().getValue() != 0) {
					EvalObj removedObj = deque.remove();
					if (count.get(removedObj.getValue()) != null) {
						Integer value = count.get(removedObj.getValue());
						count.put(removedObj.getValue(), value + 1);
					} else {
						count.put(removedObj.getValue(), 1);
					}
				} else {
					deque.remove();
				}
			} else {
				List<Integer> subpiles = calculateSubBoxPiles(deque.getFirst().getValue(), p);
				for (Integer integer : subpiles) {
					if (integer != 0) {
						deque.add(new EvalObj(integer));
					}
				}
				deque.remove();
			}
		}

		for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
			totalP += entry.getValue();
		}

//		LOG.info(count.toString());
//		LOG.info("Answer: " + totalP);
		return totalP;
	}

	public List<Integer> calculateSubBoxPiles(Integer n, Integer p) {
		List<Integer> piles = new ArrayList<>();
		for (int i = 0; i < p; i++) {
			piles.add(Math.floorDiv(n, p));
		}

		for (int i = 0; i < (n % p); i++) {
			piles.set(i, piles.get(i) + 1);
		}

		return piles;
	}

}
