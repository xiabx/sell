package com.xia.sell.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
		// set
		stringRedisTemplate.opsForValue().set("key", "value");
		// get
		String value = stringRedisTemplate.opsForValue().get("key");
		System.out.println(value);
		//Assert.assertEquals("value", value);
	}

	@Test
	public void testObj() throws Exception {
		User user = new User("king",  18);
		ValueOperations<String, User> operations = redisTemplate.opsForValue();
		// set
		operations.set("obj.user.key", user);
		// get
		System.out.println(operations.get("obj.user.key"));
		User user2 = operations.get("obj.user.key");
		System.out.println(user2.getUsername());
	}
	@Test
	public void test2(){
		GeoOperations geo = redisTemplate.opsForGeo();
		//Point point = new Point(126.55407, 43.838951);
		//geo.add("location", point,"shop1");
		//geo.add("location", new Point(126.542191,),"shop1");


		//HashMap<String, Point> map = new HashMap<>();
		//map.put("shop1", new Point(126.55407, 43.838951));
		//map.put("shop2", new Point(126.55417, 43.838951));
		//map.put("shop3", new Point(126.55427, 43.838951));
		//map.put("shop4", new Point(126.55437, 43.838951));
		//geo.add("location",map);

		//Distance distance = geo
		//		.distance("location","shop1","shop2", RedisGeoCommands.DistanceUnit.MILES);
		//System.out.println(distance);

		//longitude,latitude
		//Circle circle = new Circle(126.55437,43.838951, 500000);
		//RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
		//GeoResults results = geo.radius("location","shop2",5000);
		//System.out.println(results);
		//Circle circle = new Circle(new Point(75.22, 85.88), 800);
		//new Distance()
		//GeoResults location = geo.radius("location", circle);
		//System.out.println(location);
		//List<Point> points = redisTemplate.opsForGeo().position("lala","gaga");
		//System.out.println(points);
		//redisTemplate.opsForGeo().add(, )

		Circle circle = new Circle(126.55437, 43.838951, 800);
		//Circle circle = new Circle(116.405285,39.904989, 80000);
		RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
		GeoResults<RedisGeoCommands.GeoLocation<String>> results = geo.radius("location",circle,args);
		System.out.println(results);



		//Long geoadd = geo.geoadd("Sicily", 13.361389, 38.115556, "Palermo");

		//new RedisGeoCommands.GeoLocation<String>()
		//geo.add("hihi", )
		//new Member
		//geo.add(, )
	}
}
