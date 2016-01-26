package com.msu.moo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class CImplementationComparator {

	public static void main(String[] args) {

		DoubleSummaryStatistics stat = new DoubleSummaryStatistics();
		
		for (int i = 1; i < 30; i++) {

			final String path = String.format("comparison/zdt3_%s.out", i);

			try (Stream<String> stream = Files.lines(Paths.get(path))) {

				List<String> list = stream.collect(Collectors.toList());

				SolutionSet<Integer> set = new SolutionSet<>();

				for (String line : list) {

					String[] values = line.split("\\t");
					Solution<Integer> s = new Solution<Integer>(1,
							Arrays.asList(Double.valueOf(values[0]), Double.valueOf(values[1])));
					set.add(s);

				}

				Double hv = Hypervolume.calculate(set, Arrays.asList(1.01, 1.01));
				stat.accept(hv);
				System.out.println(hv);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		System.out.println("---------------------------");
		System.out.println(stat.getMin());
		System.out.println(stat.getAverage());
		System.out.println(stat.getMax());

	}

}