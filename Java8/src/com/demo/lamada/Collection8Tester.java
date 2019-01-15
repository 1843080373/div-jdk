package com.demo.lamada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.demo.entity.EarthModel;
import com.demo.entity.PersonModel;
import com.demo.entity.TeaModel;

public class Collection8Tester {
	public static void main(String[] args) {
		//Collection8Tester.toGroupTest();
		PersonModel personModel=new PersonModel();

        //����Ϊ������ -
        Optional<Object> o = Optional.of(personModel);
        System.out.println(o.isPresent()?o.get():"-");

        //����Ϊ������ -
        Optional<String> name = Optional.ofNullable(personModel.getName());
        System.out.println(name.isPresent()?name.get():"-");

        //�����Ϊ�գ�����xxx
        Optional.ofNullable("test").ifPresent(na->{
            System.out.println(na+"ifPresent");
        });

        //����գ��򷵻�ָ���ַ���
        System.out.println(Optional.ofNullable(null).orElse("-"));
        System.out.println(Optional.ofNullable("1").orElse("-"));

        //����գ��򷵻� ָ�����������ߴ���
        System.out.println(Optional.ofNullable(null).orElseGet(()->{
            return "hahah";
        }));
        System.out.println(Optional.ofNullable("1").orElseGet(()->{
            return "hahah";
        }));

        //����գ�������׳��쳣
        System.out.println(Optional.ofNullable("1").orElseThrow(()->{
            throw new RuntimeException("ss");
        }));


//        Objects.requireNonNull(null,"is null");


        //���� Optional ���ж༶�ж�
        EarthModel earthModel1 = new EarthModel();
        //old
        if (earthModel1!=null){
            if (earthModel1.getTea()!=null){
                //...
            }
        }
        //new
      /*  Optional.ofNullable(earthModel1)
                .map(EarthModel::getTea)
                .map(TeaModel::getType)
                .isPresent();*/


//        Optional<EarthModel> earthModel = Optional.ofNullable(new EarthModel());
//        Optional<List<PersonModel>> personModels = earthModel.map(EarthModel::getPersonModels);
//        Optional<Stream<String>> stringStream = personModels.map(per -> per.stream().map(PersonModel::getName));


        //�ж϶����е�list
        /*Optional.ofNullable(new EarthModel())
                .map(EarthModel::getPersonModels)
                .map(pers->pers
                        .stream()
                        .map(PersonModel::getName)
                        .collect(toList()))
                .ifPresent(per-> System.out.println(per));


        List<PersonModel> models=Data.getData();
        Optional.ofNullable(models)
                .map(per -> per
                        .stream()
                        .map(PersonModel::getName)
                        .collect(toList()))
                .ifPresent(per-> System.out.println(per));*/
	}
	/**
     * toList
     */
    public static void toListTest(){
        List<PersonModel> data = Data.getData();
        List<String> collect = data.stream()
                .map(PersonModel::getName)
                .collect(Collectors.toList());
    }

    /**
     * toSet
     */
    public static void toSetTest(){
        List<PersonModel> data = Data.getData();
        Set<String> collect = data.stream()
                .map(PersonModel::getName)
                .collect(Collectors.toSet());
    }

    /**
     * toMap
     */
    public static void toMapTest(){
        List<PersonModel> data = Data.getData();
        Map<String, Integer> collect = data.stream()
                .collect(
                        Collectors.toMap(PersonModel::getName, PersonModel::getAge)
                );

        data.stream()
                .collect(Collectors.toMap(per->per.getName(), value->{
            return value+"1";
        }));
    }

    /**
     * ָ������
     */
    public static void toTreeSetTest(){
        List<PersonModel> data = Data.getData();
        TreeSet<PersonModel> collect = data.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        System.out.println(collect);
    }

    /**
     * ����
     */
    public static void toGroupTest(){
        List<PersonModel> data = Data.getData();
        Map<Boolean, List<PersonModel>> collect = data.stream()
                .collect(Collectors.groupingBy(per -> "��".equals(per.getSex())));
        System.out.println(collect);
    }

    /**
     * �ָ�
     */
    public static void toJoiningTest(){
        List<PersonModel> data = Data.getData();
        String collect = data.stream()
                .map(personModel -> personModel.getName())
                .collect(Collectors.joining(",", "{", "}"));
        System.out.println(collect);
    }

    /**
     * �Զ���
     */
    public static void reduce(){
        List<String> collect = Stream.of("1", "2", "3").collect(
                Collectors.reducing(new ArrayList<String>(), x -> Arrays.asList(x), (y, z) -> {
                    y.addAll(z);
                    return y;
                }));
        System.out.println(collect);
    }
	/**
	 * �������е�����
	 */
	public static void fiterSex() {
		List<PersonModel> data = Data.getData();

		// old
		List<PersonModel> temp = new ArrayList<>();
		for (PersonModel person : data) {
			if ("��".equals(person.getSex())) {
				temp.add(person);
			}
		}
		System.out.println(temp);
		// new
		List<PersonModel> collect = data.stream().filter(person -> "��".equals(person.getSex()))
				.collect(Collectors.toList());
		System.out.println(collect);
	}
	public static void reduceTest(){
        //�ۼӣ���ʼ��ֵ�� 10
        Integer reduce = Stream.of(1, 2, 3, 4)
                .reduce(10, (count, item) ->{
            System.out.println("count:"+count);
            System.out.println("item:"+item);
            return count + item;
        } );
        System.out.println(reduce);

        Integer reduce1 = Stream.of(1, 2, 3, 4)
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce1);

        String reduce2 = Stream.of("1", "2", "3")
                .reduce("0", (x, y) -> (x + "," + y));
        System.out.println(reduce2);
    }

	public static void flatMapString() {
		/*List<PersonModel> data = Data.getData();
		// �������Ͳ�һ��
		List<String> collect = data.stream().flatMap(person -> Arrays.stream(person.getName().split(" ")))
				.collect(Collectors.toList());

		List<Stream<String>> collect1 = data.stream().map(person -> Arrays.stream(person.getName().split(" ")))
				.collect(Collectors.toList());

		// ��mapʵ��
		List<String> collect2 = data.stream().map(person -> person.getName().split(" ")).flatMap(Arrays::stream)
				.collect(Collectors.toList());
		// ��һ�ַ�ʽ
		List<String> collect3 = data.stream().map(person -> person.getName().split(" "))
				.flatMap(str -> Arrays.asList(str).stream()).collect(Collectors.toList());*/
	}

	/**
	 * ȡ�����е��û�����
	 */
	public static void getUserNameList() {
		List<PersonModel> data = Data.getData();

		// old
		List<String> list = new ArrayList<>();
		for (PersonModel persion : data) {
			list.add(persion.getName());
		}
		System.out.println(list);

		// new 1
		List<String> collect = data.stream().map(person -> person.getName()).collect(Collectors.toList());
		System.out.println(collect);

		// new 2
		List<String> collect1 = data.stream().map(PersonModel::getName).collect(Collectors.toList());
		System.out.println(collect1);

		// new 3
		List<String> collect2 = data.stream().map(person -> {
			System.out.println(person.getName());
			return person.getName();
		}).collect(Collectors.toList());
	}

	/**
	 * �������е����� ����С��20��
	 */
	public static void fiterSexAndAge() {
		List<PersonModel> data = Data.getData();

		// old
		List<PersonModel> temp = new ArrayList<>();
		for (PersonModel person : data) {
			if ("��".equals(person.getSex()) && person.getAge() < 20) {
				temp.add(person);
			}
		}

		// new 1
		List<PersonModel> collect = data.stream().filter(person -> {
			if ("��".equals(person.getSex()) && person.getAge() < 20) {
				return true;
			}
			return false;
		}).collect(Collectors.toList());
		// new 2
		List<PersonModel> collect1 = data.stream()
				.filter(person -> ("��".equals(person.getSex()) && person.getAge() < 20)).collect(Collectors.toList());
	}
}
