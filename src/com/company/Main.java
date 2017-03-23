package com.company;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.*;

interface CheckPerson{
    boolean test(Person p);
}

class CheckPersonEligibleForSelectiveService implements CheckPerson{
    @Override
    public boolean test(Person p) {
        return p.gender == Person.Sex.FEMALE &&
                p.getAge() >= 18 &&
                p.getAge() < 24;
    }
}

public class Main {

    private static List<Person> persons = new ArrayList<>();

    static void initPersons(Collection<Person> persons) {
        persons.add(new Person("Ana", LocalDate.of(1990, 12, 31), Person.Sex.FEMALE, "ana@gmail.com"));
        persons.add(new Person("Diana", LocalDate.of(1992, 1, 1), Person.Sex.FEMALE, "diana@gmail.com"));
        persons.add(new Person("Roxana", LocalDate.of(1995, 6, 22), Person.Sex.FEMALE, "roxana@gmail.com"));
        persons.add(new Person("Robert", LocalDate.of(1988, 4, 23), Person.Sex.MALE, "robert@gmail.com"));
        persons.add(new Person("Cristi", LocalDate.of(1984, 8, 7), Person.Sex.MALE, "cristi@gmail.com"));
    }

    private static void printPersonsOlderThan(List<Person> team, int age){
        for(Person p : team){
            if(p.getAge() >= age){
                p.printPerson();
            }
        }
    }

    private static void printPersonsWithinAgeRange(List<Person> team, int low, int high){
        for(Person p : team){
            if(p.getAge() >= low && p.getAge() < high){
                p.printPerson();
            }
        }
    }

    private static void printPersons(
            List<Person> team, CheckPerson tester){
        for(Person p : team){
            if(tester.test(p)){
                p.printPerson();
            }
        }
    }

    private static void printPersonWithPredicate(List<Person> team,
                                                 Predicate<Person> tester) {
        for (Person p : team) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    private static void processPersons(List<Person> team,
                                       Predicate<Person> tester,
                                       Consumer<Person> block){
        for (Person p : team) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

    private static void processPersonsWithFunction(
            List<Person> team,
            Predicate<Person> tester,
            Function<Person, String> mapper,
            Consumer<String> block) {
        for (Person p : team) {
            if (tester.test(p)) {
                String data = mapper.apply(p);
                block.accept(data);
            }
        }
    }

    private static <X, Y> void processElements(
            Iterable<X> source,
            Predicate<X> tester,
            Function<X, Y> mapper,
            Consumer<Y> block){
        for(X element : source){
            if(tester.test(element)){
                Y data = mapper.apply(element);
                block.accept(data);
            }
        }
    }

    public static void main(String[] args) {
        initPersons(persons);

        System.out.println("\nApproach 1: Create Methods That Search for Members That Match One Characteristic");
        printPersonsOlderThan(persons, 15);
        printPersonsOlderThan(persons, 30);

        System.out.println("\nApproach 2: Create More Generalized Search Methods");
        printPersonsWithinAgeRange(persons, 18, 28);

        System.out.println("\nApproach 3: Specify Search Criteria Code in a Local Class");
        printPersons(persons, new CheckPersonEligibleForSelectiveService());

        System.out.println("\nApproach 4: Specify Search Criteria Code in an Anonymous Class");
        printPersons(persons,
                new CheckPerson() {
                    @Override
                    public boolean test(Person p) {
                        return p.gender == Person.Sex.FEMALE &&
                                p.getAge() >= 18 &&
                                p.getAge() < 24;
                    }
                });

        System.out.println("\nApproach 5: Specify Search Criteria Code with a Lambda Expression");
        printPersons(persons,
                (Person p) -> p.getGender() == Person.Sex.FEMALE
                        && p.getAge() >= 18
                        && p.getAge() < 24);
        System.out.println("\nApproach 6: Use Standard Functional Interfaces with Lambda Expressions");

        printPersonWithPredicate(persons,
                (Person p) -> p.getGender() == Person.Sex.FEMALE
                        && p.getAge() >= 18
                        && p.getAge() < 24);

        System.out.println("\nApproach 7: Use Lambda Expressions Throughout Your Application");
        processPersons(persons,
                (Person p) -> p.getGender() == Person.Sex.FEMALE
                        && p.getAge() >= 18
                        && p.getAge() < 24,
                p -> p.printPerson());

        System.out.println();
        processPersonsWithFunction(persons,
                (Person p) -> p.getGender() == Person.Sex.FEMALE
                        && p.getAge() >= 18
                        && p.getAge() < 24,
                p -> p.getEmailAddress(),
                retStr -> System.out.println(retStr)
        );

        System.out.println("\nApproach 8: Use Generics More Extensively");
        processElements(persons,
                p -> p.getGender() == Person.Sex.FEMALE
                        && p.getAge() >= 18
                        && p.getAge() < 24,
                p -> p.getEmailAddress(),
                email -> System.out.println(email)
        );


        System.out.println("\nApproach 9: Use Aggregate Operations That Accept Lambda Expressions as Parameters");
        persons
                .stream()
                .filter(
                        p -> p.getGender() == Person.Sex.FEMALE
                                && p.getAge() >= 18
                                && p.getAge() < 24)
                .map(p -> p.getEmailAddress())
                .forEach(email -> System.out.println(email));

        System.out.println("\nApproach 10: Passing Lambdas with more parameters");
        Calculator calc = new Calculator();
        Calculator.IntegerMath addition = (a, b) -> a + b;
        Calculator.IntegerMath subtraction = (a, b) -> a - b;
        System.out.println("12 + 43 = " + calc.operateBinary(12, 43, addition));
        System.out.println("34 - 147 = " + calc.operateBinary(34, 147, subtraction));

        System.out.println("\nApproach 11: Capturing variables inside lambda expressions from the enclosing scope");
        LambdaScopeTest st = new LambdaScopeTest();
        LambdaScopeTest.FirstLevel fl = st.new FirstLevel(); // careful at this syntax!
        fl.methodInFirstLevel(334);
    }
}
