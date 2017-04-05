package fr.exp.databases.mongodb;

import static com.mongodb.client.model.Filters.gt;
import static java.util.Arrays.asList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MongoDBTest {

	private String serverName = "localhost"; // Usually it's 'localhost'
	private int serverPort = 27017; // Usually it's '8080'
	MongoClient mongoClient;
	MongoDatabase testDB;

	public MongoDBTest() {
		
		// A LIRE  https://www.mongodb.com/blog/post/getting-started-with-mongodb-and-java-part-i
		// https://mongodb.github.io/mongo-java-driver/3.4/driver/getting-started/quick-start/
		// http://www.mkyong.com/mongodb/java-mongodb-hello-world-example/
		
		
		// To directly connect to a single MongoDB server
		// (this will not auto-discover the primary even if it's a member of a
		// replica set)
		// Connect to a MongoDB instance running on the localhost on the default
		// port 27017

		openMongoDBConnection();
		testDB = mongoClient.getDatabase("test");
		// insertDocument();

		// find();
		// update();
		// remove();
		// aggregate();
		// indexes();
		// insertDocument2();
		// insertDocument3();
		find("insertTest");
		closeMongoDBConnection();
	}

	private final void insertDocument() {
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);

			Document doc = new Document("address",
					new Document().append("street", "2 Avenue").append("zipcode", "10075").append("building", "1480")
							.append("coord",
									asList(-73.9557413, 40.7720266)))
											.append("borough", "Manhattan")
											.append("cuisine",
													"Italian")
											.append("grades",
													asList(new Document()
															.append("date", format.parse("2014-10-01T00:00:00Z"))
															.append("grade", "A").append("score",
																	11),
															new Document()
																	.append("date",
																			format.parse("2014-01-16T00:00:00Z"))
																	.append("grade", "B").append("score", 17)))
											.append("name", "Vella").append("restaurant_id", "41704620");

			testDB.getCollection("insertTest").insertOne(doc);

		} catch (Exception e) {
			System.err.println("Error connecting to MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	private final void insertDocument2() {
		try {

			Document documentDetail = new Document();
			documentDetail.put("records", 99);
			documentDetail.put("index", "vps_index1");
			documentDetail.put("active", "true");

			Document document = new Document();
			document.put("database", "mkyongDB");
			document.put("table", "hosting");
			document.put("detail", documentDetail);

			testDB.getCollection("insertTest").insertOne(document);
		} catch (Exception e) {
			System.err.println("Error connecting to MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	private final void insertDocument3() {
		try {
			Map<String, Object> documentMap = new HashMap<String, Object>();
			documentMap.put("databaseazerty", "mkyongDBdfd");
			documentMap.put("tableaqs", "hostingefd");

			Map<String, Object> documentMapDetail = new HashMap<String, Object>();
			documentMapDetail.put("recordssd", 99);
			documentMapDetail.put("indexsd", "vps_index1dd");
			documentMapDetail.put("activesz", "trueds");

			documentMap.put("detaildcv", documentMapDetail);

			testDB.getCollection("insertTest").insertOne(new Document(documentMap));

		} catch (Exception e) {
			System.err.println("Error connecting to MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public final void closeMongoDBConnection() {
		try {
			mongoClient.close();
		} catch (Exception e) {
			System.err.println("Error in terminating connection");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public final void openMongoDBConnection() {
		try {
			mongoClient = new MongoClient(serverName, serverPort);

			// MongoClient mongoClient = new MongoClient();

			// or
			// MongoClient mongoClient = new MongoClient( "localhost" );

			// or
			// or, to connect to a replica set, with auto-discovery of the
			// primary,
			// supply a seed list of members
			// MongoClient mongoClient = new MongoClient(
			// Arrays.asList(new ServerAddress("localhost", 27017),
			// new ServerAddress("localhost", 27018),
			// new ServerAddress("localhost", 27019)));

			// or use a connection string
			// MongoClientURI connectionString = new
			// MongoClientURI("mongodb://localhost:27017,localhost:27018,localhost:27019");
			// MongoClient mongoClient = new MongoClient(connectionString);
		} catch (Exception e) {
			System.err.println("Error in terminating connection");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public final void find() {

		try {

			// queries for all documents in the restaurants collection
			// FindIterable<Document> iterable =
			// testDB.getCollection("restaurants").find();
			// FindIterable<Document> iterable = db.getCollection("restaurants")
			// .find(new Document("borough", "Manhattan"));
			// // Using the static Filters helper(s), you can also specify the
			// query as follows:
			// db.getCollection("restaurants").find(eq("borough", "Manhattan"));

			// FindIterable<Document> iterable =
			// db.getCollection("restaurants").find(
			// new Document("address.zipcode", "10075"));

			// db.getCollection("restaurants").find(eq("address.zipcode",
			// "10075"));

			// FindIterable<Document> iterable =
			// db.getCollection("restaurants").find(
			// new Document("grades.grade", "B"));

			// db.getCollection("restaurants").find(eq("grades.grade", "B"));

			// FindIterable<Document> iterable =
			// db.getCollection("restaurants").find(
			// new Document("grades.score", new Document("$gt", 30)));

			FindIterable<Document> iterable = testDB.getCollection("restaurants").find(gt("grades.score", 30));

			// FindIterable<Document> iterable =
			// db.getCollection("restaurants").find(
			// new Document("grades.score", new Document("$lt", 10)));

			// db.getCollection("restaurants").find(lt("grades.score", 10));

			// FindIterable<Document> iterable =
			// db.getCollection("restaurants").find(
			// new Document("cuisine", "Italian").append("address.zipcode",
			// "10075"));

			// db.getCollection("restaurants").find(and(eq("cuisine",
			// "Italian"), eq("address.zipcode", "10075")));

			// FindIterable<Document> iterable =
			// db.getCollection("restaurants").find(
			// new Document("$or", asList(new Document("cuisine", "Italian"),
			// new Document("address.zipcode", "10075"))));

			// db.getCollection("restaurants").find(or(eq("cuisine", "Italian"),
			// eq("address.zipcode", "10075")));

			// FindIterable<Document> iterable =
			// db.getCollection("restaurants").find()
			// .sort(new Document("borough", 1).append("address.zipcode", 1));

			// db.getCollection("restaurants").find().sort(ascending("borough",
			// "address.zipcode"));
			iterable.forEach(new Block<Document>() {
				@Override
				public void apply(final Document document) {
					System.out.println(document);
				}
			});

		} catch (Exception e) {
			System.err.println("Error finding in MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public final void find(String collectionName) {

		FindIterable<Document> iterable = testDB.getCollection(collectionName).find();

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				System.out.println(document);
			}
		});

	}

	public final void update() {
		try {

			// The following operation updates the first document with name
			// equal to "Juni", using the $set operator to update the cuisine
			// field and the $currentDate operator to update the lastModified
			// field with the current date.

			// Update Top-Level Fields
			UpdateResult ur = testDB.getCollection("restaurants").updateOne(new Document("name", "Juni"),
					new Document("$set", new Document("cuisine", "American (New)")).append("$currentDate",
							new Document("lastModified", true)));

			System.out.println("ModifiedCount : " + ur.getModifiedCount());

			// Update an Embedded Field
			ur = testDB.getCollection("restaurants").updateOne(new Document("restaurant_id", "41156888"),
					new Document("$set", new Document("address.street", "East 31st Street")));
			System.out.println("ModifiedCount : " + ur.getModifiedCount());

			// Update Multiple Documents
			ur = testDB.getCollection("restaurants").updateMany(
					new Document("address.zipcode", "10016").append("cuisine", "Other"),
					new Document("$set", new Document("cuisine", "Category To Be Determined")).append("$currentDate",
							new Document("lastModified", true)));
			System.out.println("ModifiedCount : " + ur.getModifiedCount());

		} catch (Exception e) {
			System.err.println("Error updating MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public final void remove() {

		try {
			// The following operation removes all documents that match the
			// specified condition.
			DeleteResult dl = testDB.getCollection("restaurants").deleteMany(new Document("borough", "Manhattan"));
			System.out.println("DeletedCount : " + dl.getDeletedCount());

			// Remove all
			dl = testDB.getCollection("restaurants").deleteMany(new Document());
			System.out.println("DeletedCount : " + dl.getDeletedCount());

			// The remove all operation only removes the documents from the
			// collection. The collection itself, as well as any indexes for the
			// collection, remain. To remove all documents from a collection, it
			// may be more efficient to drop the entire collection, including
			// the indexes, and then recreate the collection and rebuild the
			// indexes. Use the drop method to drop a collection, including any
			// indexes.
			testDB.getCollection("restaurants").drop();

		} catch (Exception e) {
			System.err.println("Error updating MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	public final void aggregate() {
		AggregateIterable<Document> iterable;
		// Use the $group stage to group by a specified key. In the $group
		// stage, specify the group by key in the _id field. $group accesses
		// fields by the field path, which is the field name prefixed by a
		// dollar sign $. The $group stage can use accumulators to perform
		// calculations for each group. The following example groups the
		// documents in the restaurants collection by the borough field and uses
		// the $sum accumulator to count the documents for each group.
		// iterable = testDB.getCollection("restaurants").aggregate(asList(
		// new Document("$group", new Document("_id",
		// "$borough").append("count", new Document("$sum", 1)))));

		// Use the $match stage to filter documents. $match uses the MongoDB
		// query syntax. The following pipeline uses $match to query the
		// restaurants collection for documents with borough equal to "Queens"
		// and cuisine equal to Brazilian. Then the $group stage groups the
		// matching documents by the address.zipcode field and uses the $sum
		// accumulator to calculate the count. $group accesses fields by the
		// field path, which is the field name prefixed by a dollar sign $.
		iterable = testDB.getCollection("restaurants")
				.aggregate(asList(
						new Document("$match", new Document("borough", "Queens").append("cuisine", "Brazilian")),
						new Document("$group",
								new Document("_id", "$address.zipcode").append("count", new Document("$sum", 1)))));

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				System.out.println(document.toJson());
			}
		});

	}

	public final void indexes() {

		// String s;
		// s = testDB.getCollection("restaurants").createIndex(new
		// Document("cuisine", 1).append("address.zipcode", -1));
		// System.out.println(s);

		// s = testDB.getCollection("products").createIndex( { "item": 1,
		// "stock": 1 } );
		// System.out.println(s);

		FindIterable<Document> iterable = testDB.getCollection("products").find(new Document("item", "Banana"));

		iterable.forEach(new Block<Document>() {
			@Override
			public void apply(final Document document) {
				System.out.println(document.toJson());
			}
		});
	}

}
