package net.demilich.metastone.game.cards.desc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.demilich.metastone.game.spells.desc.valueprovider.RandomValueProvider;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderArg;
import net.demilich.metastone.game.spells.desc.valueprovider.ValueProviderDesc;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ValueProviderDeserializerTest {
	@Test
	public void testDeserialize() throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ValueProviderDesc.class, new ValueProviderDeserializer());
		Gson gson = gsonBuilder.create();
		String valueProvider = "{\n" +
				"\t\t\t\t\t\"class\": \"RandomValueProvider\",\n" +
				"\t\t\t\t\t\"min\": 2,\n" +
				"\t\t\t\t\t\"max\": 4\n" +
				"\t\t\t\t}";
		RandomValueProvider rvp = new RandomValueProvider(gson.fromJson(valueProvider, ValueProviderDesc.class));
		assertNotNull(rvp);
		Field declaredField = RandomValueProvider.class.getSuperclass().getDeclaredField("desc");
		declaredField.setAccessible(true);
		ValueProviderDesc desc = (ValueProviderDesc) declaredField.get(rvp);
		assertEquals(2, desc.getInt(ValueProviderArg.MIN));
		assertEquals(4, desc.getInt(ValueProviderArg.MAX));
	}

	@Test
	public void testSerialize() throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(ValueProviderDesc.class, new ValueProviderDeserializer());
		Gson gson = gsonBuilder.create();

		Map<ValueProviderArg, Object> map = ValueProviderDesc.build(RandomValueProvider.class);
		map.put(ValueProviderArg.MIN, 2);
		map.put(ValueProviderArg.MAX, 4);
		ValueProviderDesc valueProviderDesc = new ValueProviderDesc(map);
		RandomValueProvider rvp = new RandomValueProvider(valueProviderDesc);
		String valueProvider = gson.toJson(valueProviderDesc);
		assertNotNull(rvp);
		RandomValueProvider rvp1 = (RandomValueProvider) (gson.fromJson(valueProvider, ValueProviderDesc.class).create());
		assertNotNull(rvp1);

		Field declaredField = RandomValueProvider.class.getSuperclass().getDeclaredField("desc");
		declaredField.setAccessible(true);
		ValueProviderDesc desc = (ValueProviderDesc) declaredField.get(rvp);
		ValueProviderDesc desc1 = (ValueProviderDesc) declaredField.get(rvp1);
		assertEquals(desc.getInt(ValueProviderArg.MIN), desc1.getInt(ValueProviderArg.MIN));
		assertEquals(desc.getInt(ValueProviderArg.MAX), desc1.getInt(ValueProviderArg.MAX));
	}
}