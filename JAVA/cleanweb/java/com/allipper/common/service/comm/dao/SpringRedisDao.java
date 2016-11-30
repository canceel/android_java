package com.allipper.common.service.comm.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allipper.common.service.comm.service.JedisClusterFactory;
import com.allipper.common.service.comm.utils.JedisSerializationUtils;


import redis.clients.jedis.JedisCluster;
/*
 * @author 
 *
 */
@Component
public class SpringRedisDao{
	
	@Autowired
	JedisClusterFactory jedisClusterFactory;
	
	/**
	 * 取得JedisCluster对象
	 * @return
	 * @throws Exception
	 */
	public JedisCluster getJedisCluster() throws Exception{
		JedisCluster cluster=jedisClusterFactory.getObject();
		return cluster;
	}
	/**
	 * 在指定的key中填写value值
	 * 对于已经设置了expire的数据如果使用此方法，会造成expire失效
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void add(String key,String value) throws Exception{
			jedisClusterFactory.getObject().set(key, value);
	}
	/**
	 * 将对象保存到redis中
	 * @param key
	 * @param obj
	 * @throws Exception
	 */
	public void add(String key,Object obj)throws Exception{
		jedisClusterFactory.getObject().set(JedisSerializationUtils.fastSerialize(key), JedisSerializationUtils.fastSerialize(obj));
	}
	/**
	 * 从redis中取出对象
	 * @param key
	 * @return 返回自定义对象
	 * @throws Exception
	 */
	
	public Object get(String key)throws Exception{
		byte[] objByte=jedisClusterFactory.getObject().get(JedisSerializationUtils.fastSerialize(key));
		return JedisSerializationUtils.fastDeserialize(objByte);
	}
	/**
	 * 从redis中取出对象
	 * @param key
	 * @return 返回自定义对象
	 * @throws Exception
	 */
	
	public String get_str(String key)throws Exception{
		String str=jedisClusterFactory.getObject().get(key);
		return str;
	}
	
	
	/**
	 * 根据Key移除对象
	 * @param key
	 * @throws Exception
	 */
	public void del(String key) throws Exception{
		jedisClusterFactory.getObject().del(key);
	}
	
	
	/**
	 * 判断key是否已经存在
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String key) throws Exception{
		return jedisClusterFactory.getObject().exists(JedisSerializationUtils.fastSerialize(key));
	}
	/**
	 * 判断key是否已经存在
	 * @param key string
	 * @return
	 * @throws Exception
	 */
	public boolean exists_str(String key) throws Exception{
		return jedisClusterFactory.getObject().exists(key);
	}
	/**
	 * 设置key对应的对象的存在时间
	 * @param key
	 * @param seconds
	 * @return
	 * @throws Exception 
	 */
	public long addExpire(String key,int seconds) throws Exception{
		return jedisClusterFactory.getObject().expire(key, seconds);
	}
	
	
	/**
	 * 设置key(byte)对应的对象的存在时间
	 * @param key
	 * @param seconds
	 * @return
	 * @throws Exception 
	 */
	public long addExpire_byte(String key,int seconds) throws Exception{
		return jedisClusterFactory.getObject().expire(JedisSerializationUtils.fastSerialize(key), seconds);
	}
	
	/**
	 * Key/Value形式的数据，seconds表示数据在内存中存活时间。
	 * 注意：对于已经设置了expire的数据，如果使用add的话expire会失效
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 * @throws Exception
	 */
	public long addKVExpire(String key,String value,int seconds) throws Exception{
		JedisCluster cluster=jedisClusterFactory.getObject();
		cluster.set(key, value);
		return cluster.expire(key, seconds);
	}
    
	
	/**
	 * 在指定Key所关联的List Value的尾部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的尾部插入。
	 * 如果该键的Value不是链表类型，该命令将返回相关的错误信息
	 * @param key
	 * @param value
	 * @throws Exception 
	 */
	public void rpush(String key,String value) throws Exception{
		jedisClusterFactory.getObject().rpush(key.getBytes(), value.getBytes());
	}
	
	/**
	 * 将自定义对象保存到redis的List类型的列表中
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void rpush(String key,Object value) throws Exception{
		jedisClusterFactory.getObject().rpush(JedisSerializationUtils.fastSerialize(key),JedisSerializationUtils.fastSerialize(value));
	}
	
	
	
	
	
	/**
	 * #在指定Key所关联的List Value的头部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，
	 * 之后再将数据从链表的头部插入。如果该键的Value不是链表类型，该命令将返回相关的错误信息。
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void lpush(String key,String value) throws Exception{
		jedisClusterFactory.getObject().lpush(key.getBytes(),value.getBytes());
	}
	
	/**
	 * 将自定义对象保存到redis的List类型的列表中
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	public void lpush(String key,Object value) throws Exception{
		jedisClusterFactory.getObject().rpush(JedisSerializationUtils.fastSerialize(key),JedisSerializationUtils.fastSerialize(value));
	}
	
	
	/**
	 * #返回指定Key关联的链表中元素的数量，如果该Key不存在，则返回0。如果与该Key关联的Value的类型不是链表，则返回相关的错误信息。
	 * @param key
	 * @throws Exception 
	 */
	public long llen( String key) throws Exception{
		return jedisClusterFactory.getObject().llen(key);
	}
	
	/**
	 * 返回指定范围内元素的列表。该命令的参数start和end都是0-based。即0表示链表头部(leftmost)的第一个元素。其中start的值也可以为负值，
	 * -1将表示链表中的最后一个元素，即尾部元素，-2表示倒数第二个并以此类推。该命令在获取元素时，start和end位置上的元素也会被取出。
	 * 如果start的值大于链表中元素的数量，空链表将会被返回。
	 * 如果end的值大于元素的数量，该命令则获取从start(包括start)开始，链表中剩余的所有元素。
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception 
	 */
	public List<String> lrange(String key, long start,long end) throws Exception{
		return jedisClusterFactory.getObject().lrange(key, start, end);
	}
	/**
	 * 将仅保留指定范围内的元素，从而保证链接中的元素数量相对恒定。start和stop参数都是0-based，0表示头部元素。和其他命令一样，
	 * start和stop也可以为负值，-1表示尾部元素。如果start大于链表的尾部，或start大于stop，该命令不错报错，而是返回一个空的链表，
	 * 与此同时该Key也将被删除。如果stop大于元素的数量，则保留从start开始剩余的所有元素。
	 * @param key
	 * @param start
	 * @param end
	 * @throws Exception 
	 */
	public void ltrim(String key,long start, long end) throws Exception{
		jedisClusterFactory.getObject().ltrim(key, start, end);
	}
	/**
	 * 返回链表中指定位置(index)的元素，index是0-based，表示头部元素，如果index为-1，表示尾部元素。
	 * 如果与该Key关联的不是链表，该命令将返回相关的错误信息
	 * @param key
	 * @param index
	 * @return
	 * @throws Exception 
	 */
	public String lindex(String key,  long index) throws Exception{
		return jedisClusterFactory.getObject().lindex(key, index);
	}
	/**
	 * 设定链表中指定位置的值为新值，其中0表示第一个元素，即头部元素，-1表示尾部元素。
	 * 如果索引值Index超出了链表中元素的数量范围，该命令将返回相关的错误信息。
	 * @param key
	 * @param index
	 * @param value
	 * @throws Exception
	 */
	public void lset( String key, long index, String value) throws Exception{
		jedisClusterFactory.getObject().lset(key, index, value);
	}
	/**
	 * 设定链表中指定位置的值为新值，其中0表示第一个元素，即头部元素，-1表示尾部元素。
	 * 如果索引值Index超出了链表中元素的数量范围，该命令将返回相关的错误信息。
	 * @param key
	 * @param index
	 * @param value
	 * @throws Exception
	 */
	public void lset(String key,long index,Object value) throws Exception{
		jedisClusterFactory.getObject().lset(JedisSerializationUtils.fastSerialize(key), index, JedisSerializationUtils.fastSerialize(value));
	}
	
	
	
	
	/**
	 * 在指定Key关联的链表中，删除前count个值等于value的元素。如果count大于0，从头向尾遍历并删除，
	 * 如果count小于0，则从尾向头遍历并删除。如果count等于0，则删除链表中所有等于value的元素。
	 * 如果指定的Key不存在，则直接返回0,返回被删除的元素数量。
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public long lrem(String key, long count,String value) throws Exception{
		return jedisClusterFactory.getObject().lrem(key, count, value);
	}
	
	/**
	 * 在指定Key关联的链表中，删除前count个值等于value的元素。如果count大于0，从头向尾遍历并删除，
	 * 如果count小于0，则从尾向头遍历并删除。如果count等于0，则删除链表中所有等于value的元素。
	 * 如果指定的Key不存在，则直接返回0,返回被删除的元素数量。
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 * @throws Exception 
	 */
	public long lrem(String key, long count,Object value) throws Exception{
		return jedisClusterFactory.getObject().lrem(JedisSerializationUtils.fastSerialize(key), count, JedisSerializationUtils.fastSerialize(value));
	}
	
	
	
	/**
	 * 返回并弹出指定Key关联的链表中的第一个元素，即头部元素。如果该Key不存，返回null。
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String lpop(String key) throws Exception{
		return jedisClusterFactory.getObject().lpop(key);
	}
	/**
	 * 返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素。如果该Key不存，返回nil。
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String  rpop(String key) throws Exception{
		return jedisClusterFactory.getObject().rpop(key);
	}
	/**
	 * 原子性的从与srckey键关联的链表尾部弹出一个元素，同时再将弹出的元素插入到与dstkey键关联的链表的头部。
	 * 如果srckey键不存在，该命令将返回null，同时不再做任何其它的操作了。如果srckey和dstkey是同一个键，
	 * 则相当于原子性的将其关联链表中的尾部元素移到该链表的头部。
	 * @param srckey
	 * @param dstkey
	 * @return
	 * @throws Exception 
	 */
	public String rpoplpush(String srckey,String dstkey) throws Exception{
		return jedisClusterFactory.getObject().rpoplpush(srckey, dstkey);
	}
	/**
	 * 批量放置List内数据到redis库
	 * @param key
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void batchRpushStrList(String key,List<String> list) throws Exception{
		if(list!=null){
		Iterator it=list.iterator();
		while(it.hasNext()){
			this.rpush(key,it.next().toString());
		}
		}
		
	}
	
	/**
	 * 批量放置List内数据到redis库
	 * @param key
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void batchRpushObjList(String key,List<Object> list) throws Exception{
		if(list!=null){
		Iterator it=list.iterator();
		while(it.hasNext()){
			this.rpush(key, it.next());
		}
		}
	}
	
	
	
	/**
	 * 批量放置List内数据到redis库
	 * @param key
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void batchLpushStrList(String key,List<String> list) throws Exception{
		if(list!=null){
		Iterator it=list.iterator();
		while(it.hasNext()){
			this.lpush(key,it.next().toString());
		}
		}
	}
	
	/**
	 * 批量放置List内数据到redis库
	 * @param key
	 * @param list
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void batchLpushObjList(String key,List<Object> list) throws Exception{
		if(list!=null){
		Iterator it=list.iterator();
		while(it.hasNext()){
			this.lpush(key,it.next());
		}
		}
	}
	
	/**
	 * 如果在插入的过程用，参数中有的成员在 Set中已经存在，该成员将被忽略，而其它成员仍将会被正常插入。
	 * 如果该Key并不存在，该命令将会创建一个新的Set，此后再将参数中 的成员陆续插入。
	 * 如果该Key的Value不是Set类型，将返回相关的错误信息。
	 * @param key
	 * @param member
	 * @throws Exception
	 */
	public void sadd(String key,String member) throws Exception{
		jedisClusterFactory.getObject().sadd(key, member);
	}
	
	
	/**
	 * 如果在插入的过程用，参数中有的成员在 Set中已经存在，该成员将被忽略，而其它成员仍将会被正常插入。
	 * 如果该Key并不存在，该命令将会创建一个新的Set，此后再将参数中 的成员陆续插入。
	 * 如果该Key的Value不是Set类型，将返回相关的错误信息。
	 * @param key
	 * @param member
	 * @throws Exception
	 */
	public void sadd(String key,Object member) throws Exception{
		jedisClusterFactory.getObject().sadd(JedisSerializationUtils.fastSerialize(key), JedisSerializationUtils.fastSerialize(member));
	}
	
	
	
	/**
	 * 获取Set中成员的数量。
	 * @param key
	 * @return 返回Set中成员的数量，如果该Key并不存在，返回0。
	 * @throws Exception
	 */
	public long scard(String key) throws Exception{
		return jedisClusterFactory.getObject().scard(key);
	}
	
	
	
	/**
	 * 判断参数中指定成员是否已经存在于与Key相关联的Set集合中。
	 * @param key
	 * @param member
	 * @return true表示已经存在，false表示不存在
	 * @throws Exception
	 */
	public Boolean sismember(String key,String member) throws Exception{
		return jedisClusterFactory.getObject().sismember(key, member);
	}
	
	/**
	 * 判断参数中指定成员是否已经存在于与Key相关联的Set集合中。
	 * @param key
	 * @param member
	 * @return true表示已经存在，false表示不存在
	 * @throws Exception
	 */
	public Boolean sismember(String key,Object obj) throws Exception{
		return jedisClusterFactory.getObject().sismember(JedisSerializationUtils.fastSerialize(key), JedisSerializationUtils.fastSerialize(obj));
	}
	
	/**
	 * 获取与该Key关联的Set中所有的成员。
	 * @param key
	 * @return 返回Set中所有的成员。
	 * @throws Exception
	 */
	public Set<String> smembers(String key) throws Exception{
	return jedisClusterFactory.getObject().smembers(key);	
	}
	
	/**
	 * 获取与该Key关联的Set中所有的成员。
	 * @param key
	 * @return 返回Set中所有的成员。
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<Object> smembersObj(String key) throws Exception{
		Set<byte[]> set=jedisClusterFactory.getObject().smembers(JedisSerializationUtils.fastSerialize(key));
		Set reset=new HashSet();
	    if(set!=null){
	    	Iterator<byte[]> it=set.iterator();
	    	while(it.hasNext()){
	    		Object obj=JedisSerializationUtils.fastDeserialize(it.next());
	    		reset.add(obj);
	    	}
	    }
	    
	    return reset;
	}
	
	
	/**
	 * 随机的移除并返回Set中的某一成员。 
	 * 由于Set中元素的布局不受外部控制，因此无法像List那样确定哪个元素位于Set的头部或者尾部。
	 * @param key
	 * @return 返回移除的成员，如果该Key并不存在，则返回nil。
	 * @throws Exception
	 */
	public String spop(String key) throws Exception{
		return jedisClusterFactory.getObject().spop(key);
	}
	
	/**
	 * 随机的移除并返回Set中的某一成员。 
	 * 由于Set中元素的布局不受外部控制，因此无法像List那样确定哪个元素位于Set的头部或者尾部。
	 * @param key
	 * @return 返回移除的成员，如果该Key并不存在，则返回nil。
	 * @throws Exception
	 */
	public Object spopObj(String key) throws Exception{
		byte[] objByte=jedisClusterFactory.getObject().spop(JedisSerializationUtils.fastSerialize(key));
		return JedisSerializationUtils.fastDeserialize(objByte);
	}
	
	/**
	 * 从与Key关联的Set中删除参数中指定的成员，不存在的参数成员将被忽略，如果该Key并不存在，将视为空Set处理。
	 * @param key
	 * @param member
	 * @return 从Set中实际移除的成员数量，如果没有则返回0。
	 * @throws Exception
	 */
	public long srem(String key,String... member) throws Exception{
		return jedisClusterFactory.getObject().srem(key, member);
	}
	
	/**
	 * 从与Key关联的Set中删除参数中指定的成员，不存在的参数成员将被忽略，如果该Key并不存在，将视为空Set处理。
	 * @param key
	 * @param member
	 * @return 从Set中实际移除的成员数量，如果没有则返回0。
	 * @throws Exception
	 */
	public long srem(String key,Object... member) throws Exception{
		return jedisClusterFactory.getObject().srem(JedisSerializationUtils.fastSerialize(key), JedisSerializationUtils.fastSerialize(member));
	}
	
	/**
	 * 和SPOP一样，随机的返回Set中的一个成员，不同的是该命令并不会删除返回的成员。
	 * @param key
	 * @return 返回随机位置的成员，如果Key不存在则返回nil。
	 * @throws Exception 
	 */
	public String srandmember(String key) throws Exception{
		return jedisClusterFactory.getObject().srandmember(key);
	}
	
	
	/**
	 * 和SPOP一样，随机的返回Set中的一个成员，不同的是该命令并不会删除返回的成员。
	 * @param key
	 * @return 返回随机位置的成员，如果Key不存在则返回nil。
	 * @throws Exception 
	 */

	public Object srandmemberObj(String key) throws Exception{
		byte[] byteobj=jedisClusterFactory.getObject().srandmember(JedisSerializationUtils.fastSerialize(key));
		return JedisSerializationUtils.fastDeserialize(byteobj);
	}
	
	
	/**
	 * 原子性的将参数中的成员从source键移入到destination键所关联的 Set中。
	 * 因此在某一时刻，该成员或者出现在source中，或者出现在destination中。
	 * 如果该成员在source中并不存在，该命令将不会再 执行任何操作并返回0，否则，该成员将从source移入到destination。
	 * 如果此时该成员已经在destination中存在，那么该命令仅是 将该成员从source中移出。
	 * 如果和Key关联的Value不是Set，将返回相关的错误信息。
	 * @param srckey
	 * @param dstkey
	 * @param member
	 * @return 1表示正常移动，0表示source中并不包含参数成员。
	 * @throws Exception
	 */
	public Long smove(String srckey, String dstkey,String member) throws Exception{
		return jedisClusterFactory.getObject().smove(srckey, dstkey, member);
	}
	/**
	 * 返回参数中第一个Key所关联的Set和其后所有Keys所关联的Sets中成员的差异。如果Key不存在，则视为空Set
	 * @param keys
	 * @return 差异结果成员的集合。
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Set sdiff(String...keys) throws Exception{
		return jedisClusterFactory.getObject().sdiff(keys);
	}
	/**
	 * 和SDIFF命令在功能上完全相同，两者之间唯一的差别是SDIFF返回差异的结果成员，
	 * 而该命令将差异成员存储在destination关联的Set中。如果destination键已经存在，该操作将覆盖它的成员。
	 * @param dstkey
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public long sdiffstore(String dstkey,String...keys) throws Exception{
		return jedisClusterFactory.getObject().sdiffstore(dstkey, keys);
	}
	/**
	 * 返回参数中所有Keys关联的Sets中成员的交集。
	 * 因此如果参数中任何一个Key关联的Set为空，或某一Key不存在，那么该命令的结果将为空集。
	 * @param keys
	 * @return 交集结果成员的集合。
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Set sinter(String...keys) throws Exception{
		return jedisClusterFactory.getObject().sinter(keys);
	}
	/**
	 * 和SINTER命令在功能上完全相同，两者之间唯一的差别是SINTER返回交集的结果成员，而该命令将交集成员存储在destination关联的Set中。
	 * 如果destination键已经存在，该操作将覆盖它的成员。
	 * @param dstkey
	 * @param keys
	 * @return 返回交集成员的数量。
	 * @throws Exception 
	 */
	public long sinterstore(String dstkey,String...keys) throws Exception{
		return jedisClusterFactory.getObject().sinterstore(dstkey, keys);
	}
	/**
	 * 该命令将返回参数中所有Keys关联的Sets中成员的并集。
	 * @param keys
	 * @return 并集结果成员的集合。
	 * @throws Exception 
	 */
	public Set<String> sunion(String...keys) throws Exception{
		return jedisClusterFactory.getObject().sunion(keys);
	}
	
	/**
	 * 该命令将返回参数中所有Keys关联的Sets中成员的并集。
	 * @param keys
	 * @return 并集结果成员的集合。
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set sunionObj(String...keys) throws Exception{
		//将字符串数组转为字节数组
		byte[][] byteA=convertStrArrTobyteArr(keys);
		Set<byte[]> set=jedisClusterFactory.getObject().sunion(byteA);
		
		Set<Object> temset=new HashSet();
		if(set!=null){
			Iterator<byte[]> it=set.iterator();
			while(it.hasNext()){
				temset.add(JedisSerializationUtils.fastDeserialize(it.next()));
			}
		}
		return temset;
	}
	
	/**
	 *sunion 在功能上完全相同，两者之间唯一的差别是SUNION返回并集的结果成员，而该命令将并集成员存储在destination关联的Set中。
	 * 如果destination键已经存在，该操作将覆盖它的成员。
	 * @param dstkey
	 * @param keys
	 * @return 返回并集成员的数量。
	 * @throws Exception
	 */
	public long sunionstore(String dstkey,String...keys) throws Exception{
		return jedisClusterFactory.getObject().sunionstore(dstkey, keys);
	}
	/**
	 * hash功能:将key、value、hashName保存到redis
	 * @param hashName String型
	 * @param key String型
	 * @param value String型
	 * @return
	 * @throws Exception
	 */
	public long hset(String hashName,String key,String value) throws Exception{
		return jedisClusterFactory.getObject().hset(hashName, key, value);
	}
	/**
	 * hash功能:将key、value、hashName保存到redis
	 * @param hashName String型
	 * @param key String型
	 * @param value long型
	 * @return
	 * @throws Exception
	 */
	public long hincrBy(String hashName,String key,long value) throws Exception{
		return jedisClusterFactory.getObject().hincrBy(hashName, key, value);
	}
	/**
	 * hash功能:判断是否存在
	 * @param hashName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean hexists(String hashName,String key) throws Exception{
		return jedisClusterFactory.getObject().hexists(hashName, key);
	}
	/**
	 * hash功能:根据hashName和 key获取value
	 * @param hashName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String hget(String hashName,String key) throws Exception{
		return jedisClusterFactory.getObject().hget(hashName, key);
	}
	/**
	 * hash功能:获取所有key值
	 * @param hashName
	 * @return
	 * @throws Exception
	 */
	public Set<String> hkeys(String hashName) throws Exception{
		return jedisClusterFactory.getObject().hkeys(hashName);
	}
	/**
	 * hash功能:根据key值集合获取对应所有value
	 * @param hashName
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	public List<String> hmget(String hashName,Set<String> keys) throws Exception{
		Iterator<String> it = keys.iterator();
		String keyStr="";
		while(it.hasNext()){
			keyStr= keyStr+'"'+it.next()+'"'+',';
		}
		if(keys.size()>0){
			keyStr.substring(0, keyStr.length()-1);
		}
		return jedisClusterFactory.getObject().hmget(hashName,keyStr);
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	private byte[][] convertStrArrTobyteArr(String... keys){
		List list=null;
		byte[][] byteArr=new byte[keys.length][];
		for(int i=0;i<keys.length;i++){
		byteArr[i] =JedisSerializationUtils.fastSerialize(keys);
		}
		return byteArr;
	}
}
