package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by APEPTAN on 3/23/2017.
 */
public class MethodReferencesTest {
    private static List<Person> team = new ArrayList<>();

    public static void main(String... args){
        Main.initPersons(team);

        Person[] teamAsArray = team.toArray(new Person[team.size()]);

        class PersonAgeComparator implements Comparator<Person>{

            @Override
            public int compare(Person a, Person b) {
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }

        System.out.println("Team Before sorting:");
        team.stream().forEach(p -> System.out.println(p));

        Arrays.sort(teamAsArray, new PersonAgeComparator());

        System.out.println("\nTeam After sorting:");
        Arrays.asList(teamAsArray).stream().forEach(p -> System.out.println(p));

        System.out.println("\nSorting in reverse order using lambda expressions:");
        Arrays.sort(teamAsArray, (Person a, Person b) -> {
            return b.getBirthday().compareTo(a.getBirthday());
        });
        Arrays.asList(teamAsArray).stream().forEach(p -> System.out.println(p));

        System.out.println("\nHowever, resort by *referencing* the already existing *method* compareByAge from Person class:");
        Arrays.sort(teamAsArray, Person::compareByAge); // much clearer
        Arrays.asList(teamAsArray).stream().forEach(p -> System.out.println(p));

        System.out.println("\nOr reverse sort referencing the same method inside lambda expression:");
        Arrays.sort(teamAsArray, (a, b) -> Person.compareByAge(b, a));
        Arrays.asList(teamAsArray).stream().forEach(p -> System.out.println(p));

        /*
        * Kinds of Method References
            There are four kinds of method references:

            Kind	Example
            Reference to a static method	                        ContainingClass::staticMethodName
            Reference to an instance method of a particular object	containingObject::instanceMethodName
            Reference to an instance method of an arbitrary object of a particular type	ContainingType::methodName
            Reference to a constructor	ClassName::new
        **/

        System.out.println("\nSort Team members by name using ComparisonProvider - reference to instance method:");
        class ComparisonProvider{
            public int compareByName(Person a, Person b){
                return a.getName().compareTo(b.getName());
            }

            public int compareByAge(Person a, Person b){
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }

        ComparisonProvider myComparisonProvider = new ComparisonProvider();
        Arrays.sort(teamAsArray, myComparisonProvider::compareByName); // 2 parameter method! - of a "comparator"
        Arrays.asList(teamAsArray).stream().forEach(p -> System.out.println(p));
        /*
         * The method reference myComparisonProvider::compareByName invokes the method compareByName that
         * is part of the object myComparisonProvider. The JRE infers the method type arguments,
         * which in this case are (Person, Person).
         */


        String[] stringArray = { "Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda" };
        System.out.println("Consider the following String[]:");
        Arrays.asList(stringArray).stream().forEach(p -> System.out.println(p));

        System.out.println("\nSort it using a reference to an instance method of an Arbitrary Object of a Particular Type:");
        Arrays.sort(stringArray, String::compareToIgnoreCase); // 1 parameter method - specific to the Type of objects being compared.

        System.out.println("\nSort using a reference to an instance method of an Arbitrary Object of a Particular Type:");
    }
}
