# Intro

to understand this readme take into account this was a small class designed for a intro level developer, it was a way to jump start his knowledge and thus might have a slow pace but the intention is to make sure he understands every step taken and is able to reproduce it and reorient when doing so on his own.

Text between < > means a variable and should be replaced with whatever the text describes.

# Step 1 creating a project in spring intializr

The first step is to create a project in the Spring intializr with nothing added on the option selections.

### Step Conclusion

For this step you might want to take a look into the pom.xml. At this point it might be a bit above your head if you are just getting started but still it would help to get familiar with the structure. So here is a small breakdown

	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.training.questionaire</groupId>
	<artifactId>qmanager</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>qmanager</name>
	<description>Training project</description>

	<properties>
		<java.version>11</java.version>
	</properties>
	
The modelVersion tag for maven 2 and 3 needs to stay at four as those versions of maven do nto support another model version. The model version tells your POM what model descriptor project it conforms to. Though currently we only use 4 this allows for if ever the model moves to a newer version there is still ways to determine which other versions are available and used in a specific pom.

the parent tag tells maven that before processing this pom it should read the pom related to the parent tag and merge it with this one, once that's done maven generates what is known as an effective pom, which is the actual pom used to process your build. Parents help unify information common to many projects instead of having each project go through the work (and risk) of setting up versions and so on.

next come the groupId,artifactId,version,name and description tags (the ones at the same level as the paren tag not the children of the paren tag) this is your projects information as far as maven is concerned.  groupId is usually the inverted domain of your company or project with a little something else to identify what group of projects this artifact belongs to in case you have many. Let's say you have a questionnare project and a reservations project and your domain is training.com your groupId for questionnaire might look something like `com.training.api.questionnare` and for your reservations it would look something like `com.training.api.reservations`

artifactId is the specific identifier of the project related to this pom. Suppose you are building a microservice-like arquitecture for your questionnare service you will have several artifacts for this project so  your artifacts might end up with names like `questionsQueryEngine` and `questionsCRUDEngine` and so on. So now having a group makes more sense as mutiple artifacts may be related to it.

the version tag is pretty self explanatory. It helps keep track of every released version of your artifact so if someone else is using it they can control what code and functionality they are getting.

the name is the recognizable name of your artifact and the description is a text to help people realize what your artifact does.

the properties sections defined variables for this pom.xml, in this case we have a java.version variable that can be referenced in other parts of this pom by using ${java.version} and it will get replaced on build time.

Next come the dependencies section:

        <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
		
This is what a single dependency looks like. The dependency tag limits the info of a single dependency and then come it's maven descriptors which we already reviewed.


this dependency has a set of tags that do not need to be included such as the scope and exclusions tags.

the scope tells maven that it should use it for a specific moment in the build process. Here for example it is being told to use it only when the app is running tests and thus will not be added to our final Jar/war file once the project is built.

the exclusions tag groups exclusion tags and is a list of dependencies that you do not want to have added to your project via the dependency in which they are in. Think of it this way you need to use a dependency called `questionData` which helps in handling database acces to question repositories, but that dependency in its pom declares that it needs `questionModel` which you already need because you are doing a lot of stuff with questions, so instead of letting `questionData` handle the version and import of that dependency you tell maven to ignore that declaration inside `QuestionData` and add your own dependency for `QuestionModel` on your pom.

and thats basically it for the pom, there are a bunch of tags that you can add to customize build process and handle additional functionalities, but we will cover them as we come to them.

# Step 2 create a GIT repository to handle versioning

Next we are going to add it to the repository using the following commands

at the project root:

### 1.- intialize the local repository

`git init` .- this ensures git (already installed in our machine) recognizes the folder as a repository and starts  to manage it and version the files.

### 2.- add all files

`git add *` .- this command adds all files to be tracked by git. it is not enough to add a file to a repository for it to be tracked, we need to add it to the actual repository, though this might seem as adding steps it allows us to have files that might work for local tests or other non-system-wide functions available inour environment without disrupting the rest of the teams setup.

### 3.- commit the files to local history

`git commit -m <Desired Message>` .- this is the initial commit, at this point the changes are saved to our local repository, even if we went to the origin we would not see them, but they have imprinted themselves into the branches history. The message should relay to whoever reads it some manner of description as to what changes occurred. if a specific bug was tied to the changes then that should also be included in the message together with any information that might be useful to people using the new version.

### 4.- point your local branch and name it

`git branch -M <branch name>` this sets up a branch in our repository. Though the command has the replace token in the actual excercise we named it main, this branch of course does not exist on our origin and even if it did the upper-case M will do the move/rename even if it exists.

### 5.- send the changes to the remote repository

`git push -u <repository> <branch>` finally this line sends the changes to the remote (origin in our case) repository. Once a push is completed the rest of the team accessing this repository will be able to see the changes and the history of the branch is now updated for everyone.

### Step Conclusion

Each steps here is described but it important to note that git has a wide-ranged functionality extending beyond this. Though we might not see it in this course it is worth taking a look at some of the more prominent features as GIT is a great tool to handle projects with a large team and quick update needs.

# Step 3 creating a simple REST API

In this step we are going to create a simple rest controller that will receive a GET HTTP Request. The hello world for this project if you will.

### 1.- Add the dependecies to the pom.xml

Add the required dependencies so spring boot can recognize components annotated with @Controller

`<dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
 <dependency>`
 
 Here the first block is the dependency wrapper which determines that the following tags describe a maven dependency, an artifact (jar,war,ear...) which this project will use. The groupdId determines the group under which the artifact is stored, usually ir represents the developings companies domain in reverse with an extra section for the projects subsection. the artifactId is the identifier of the specific dependency, as we progress we will see that many of the dependencies we will use fall under org.springframework.boot and vary on their specific artifactId.
 
 notice how this artifact has the word `starter` in it, spring boot uses starters to avoid boiler plate code and trigger a self configuring set of beans. This self configuring functionality is at the heart of spring boot as it does away with having to add each dependency and configure it to detail. Which for smaller projects might not be necessary.
 
 ### 2.- Annotate the controller class
 
 Next we will create a class in the package `com.training.questionaire.qmanager.web.rest` the class will be named `HelloWorldRestController`. Here notice two things, the all lower case usage for packages and the Camel case used for Classes, this is Java Convention and though it might be good practice for a training project take into account that each corporation might use specific naming convetions that should be followed.
 
 this class will be annotated with the `@RestController` annotation that will ensure Spring Web recognizes this class as a request handler and will try to match any URL to it's annotated methods. At the level class we will also use this annotation `@RequestMapping("< base URL mapping >")` this will append the prefix we add here to all mapping we add at method level, in this case I have added `qmanager`.
 
 ### 3.- add a handler method for the hello world functionality
 
 Now we need to add a method to return a simple text. We will start Using Response Entity so we can handle http response codes together with body content so use this definition:
 
 `    @GetMapping("/helloWorld")
      public ResponseEntity<String> sayHelloWorld(){
          return ResponseEntity.ok().body("HELLO WORLD");
      }`
      
Here the `@GetMapping` annotation determines that this method will be handling requests to `/qmanager/helloworld` the qmanager prefix comes from the RequestMapping annotatin we added to the class. since it's a method spring will be using it needs to be public so it can access it and the return type ResponseEntity determines a Spring type that makes it easier to set http response codes and body types.
 
 ### 4.- Showcase the self configuring formatting of responses
 
 Spring boot's capability to self configure also brings us the capability to easily turn our responses to JSON, we can of course change the output but for the purpose of this example we will simply use the self configured jackson converter. So lets create a class that will serve as an example response for this. 
 
 We will create class `com.training.questionaire.qmanager.web.dto.ExampleResponse` this class will have three properties a String called `messageKey` a Integer called `value` and a String called `user`. We recommend using objects instead of primitives to make use of Javas OOP capabilities(Be cautious with your usage of objects outside this excercise as primitives might be a better fit for solutions that need to have a focus on optimizing their performance). With the class created let's add a new method to our controller.
 
     @GetMapping("/helloWorldComplex")
     public ResponseEntity<ExampleResponse> sayHelloWorldWithComplexity(){
         ExampleResponse response= new ExampleResponse();
         response.setMessageKey("qmanager.message.hello-world");
         response.setValue(200);
         response.setUser("someUser");
         return ResponseEntity.ok().body(response);
     }
     
this method returns an object of type ExampleResponse and on the return it sets the code to ok and the body to the object created.
now to view the results of our work we run:

`mvn spring-boot:run`

wait for it to iniatialize and then on your browser access:

`http://localhost:8080/qmanager/helloWorldComplex` or `http://localhost:8080/qmanager/helloWorld`

each should return their own type of message and as you can see we already have a formatted responses.

### 5.- Commit the results

now to commit the results we repeat some of the steps we used initially:

`git add *` to add all files, this is not the best way to add files we should target specifics to avoid config or temp files that are not in our gitignore, but for educational purposes let's use this.

`git commit -m "<Desired Message>"` to save changes to local history

`git push` to send our changes upstream to the remote repository

### Step Conclusion

So now we know how to add a dependency, how to create a controller and how to commit and push our changes to an updatream repository.

Things to keep an eye on this step are the @RestController usage. This annotation sets up a class with both the @Controller and the @ResponseBody annotations. @Controller is a specialization of the @Component annotation and allows Spring to recognize a class so it can be handled by the spring context. the @ResponseBody has a more practical use, as instead of looking in the response for a view that matches it, it will turn the body of the response into the serialization of any object returned from the method that is annotated. 

Also keep an eye on the @GetMapping that comes in to replace @RequestMapping, where @RequestMapping would have asked for a list of all allowed methods we can now tell the method what http request method it will answer and all other will be rejected, in this case we are using a GET, if we added to this annotation a produces parameter we could make it return XML instead of JSON. if you want to try it just know that to make sure a method returns a JSON we would make the annotation look something like this:

`@GetMapping(value="/helloWorldComplex", produces = MediaType.JSON)`

I would recommend you try to make the helloWorldComplex return various types so you can see what happens with the output and get familiar with the effects of the @GetMapping

# Step 4 - Sending data to the BackEnd

Now that we have a simple way to communicate through HTTP with a JAVA application it would be nice to have a more specialized message come to us, so in this step we will create a Post Method and have it return to us a custom message with our name or whatever text we feed it. So let's start by adding a method marked with a `@PostMapping` and url `/TellMeHello`, something like this:

    @PostMapping(value="/tellMeHello")
    public ResponseEntity<ExampleResponse> tellMeHelloHandlerJSON(@RequestBody TellMeHelloRequest request){
        ExampleResponse response= new ExampleResponse();
        response.setMessageKey("qmanager.message.hello-world."+request.getUsername()+"."+request.getAge());
        response.setValue(200);
        response.setUser(request.getUsername());
        return ResponseEntity.ok().body(response);
    }

There are two main changes to our other endpoint here, one is the `@PostMapping` which we already know follows the structure of other mappers, and it simply telling us that the url defined can only be accessed by post http methods.

but we also added the @RequestBody to the parameter of the method. What this is telling it is that in the request body it might find properties that match those of the class of the parameter and should it find them it needs to create an object with those values assigned. Otherwise it will provided a non-null object with null values.

Once done we can launch our app with the same command as before:

`mvn spring-boot:run`

Once it's up and running use a Rest Client such as boomerang or postman and send a request to this url:

`http://localhost:8080/qmanager/tellMeHello`

remember to change the request type to post if you send a GET you should be getting a 405 status which means you tried to acces a URL in a way which was not allowed for that endpoint. The body of your request should look something like this:

`{
    "username": "Johnny",
    "age": 19
}`

send it and you should probably get a 415 error which is an Unsupported Media Type status code. Meaning though the formatting of your request was correct you told the endpoint you were sending plain text and it does not know how to handle plain text. so you will need to set your header `Content-type` to `application/json` so that the endpoint knows that the content of the request is in a json format and can parse it into an object for our handling. re-send the request and you should get the expected response in something like this:

`{
    "messageKey": "qmanager.message.hello-world.Johnny.19",
    "value": 200,
    "user": "Johnny"
}`

in the request change the case of any letter of the field names (username and age) and watch how the response now includes a null value. Jackson does not expect the body to match exactly with the object being expected it simply expects the property name of the object to match with those of the request body if it does not it leaves them as null.
 
### Step Conclusion

Now we know not only how to send back a dumb response but actually receive data from the client. Though we haven't really accessed the full capabilities of an app to be interacted with we have now determined that we can send complex data structures and receive them as objects on the server side to be handled normally and without further complication thanks to letting Spring boot handle conversion. Also Spring boot already limits call methods and content types to be received so we no longer have to worry about validating each end-point against certain type of requests.

# Step 5 - Creating a POJO(Plain Old Java Object) and testing the importance of the equals and hashCode methods.

Now that we have a way to receive data via http it is important to begin separating responsabilities so each component and layer can have behaviors related only to the layer it corresponds to and also to allow layers to be easily dettached from one another. For this we will forgo the previous code and create a new structure that begins to ressemble a business necessity.

First let's create a question Object which will have an id, a question, response, subject, seniority and level. for now the identifier will be a long and the others a String. Don't forget to add getters and setters so access to innstance data can be controlled by the class instead of handing control to other classes.

Once you have that implemented let's implement the hashcode and equals method. Now this is important because it helps all the JAVA API better determine what objects are equal, how their equality is determined and helps improve the way collections and other comparisons handle our custom Objects. Now implementing them can be very simple as there is no real enforcing, this of course does not mean that we should do our implementation with no thought and here is why:

-All objects who are qual must return the same haschode

-If objects have the same hashcode they can be equal but do not Have to be

-Unequal objects can return the same hashcode.

the last one of course though valid doesn't necessarily come recommended as two unequal objects returning the same hashcode will cause a drop in performance.

here is a little visual example:

__________________
1| A,D,E,F
__________________
__________________
2| B,H
__________________
__________________
3| C,G
__________________

The starting number in each row before the pipe represents the hashcode that object has, each letter is an object. Now Let's say I wanted to know if my collection (which is composed of the 3 rows) contains A, what would happen is that it's hashcode would be checked and since it's 1 it's equals method would be run against all elements in the 1 row. This way Java avoids comparing it against all elements in the collection. With this in mind we can design our hashcode and equals method.

-We can only use fields which won't constantly be changing.

-Every field we use in the hashcode should be used in the equals to ensure they both match up.

now taking into account our problem requires we create questions for a questionnaire, I suggest we use the identifier though the question itself defines it questions are made of multiple words and similar questions may exist also questions will probably change due to typos but for the moment let's assume that instead of changing a question due to typo we simply destroy it and create a new one. So with this knowledge our hashcode would look something like this:


      @Override
        public int hashCode() {
            return Objects.hash(getId());
       }


our equals now must match our hashCode so when comparng questions we should check their id. Let's build our equals method

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Question)) return false;
            Question question = (Question) o;
            return getId() == question.getId();
        }

Let's break down the equals method. First as you can see it receives a parameter of type Object, this instantly removes primitive types from the possibility of being compared (exclusing boxing and unboxing which we will review later).

next the equals method uses the operator _instanceof_ this operator checks that the object referenced by the parameters is in fact an object of type question. Here I'll stop to review a nomenclature which I'll be using throughout the rest of the course. when we declare a variable such as "Object createdObject = new Question()" we end up with two things in our hands. The variable createdObject which is a reference of type Object and the actual object of type question. So createdObject is just a pointer who will allow itself to be pointed to any object as long as that object extends from class Object at some point. In the delcaration we are initially pointing it to a Question type Object and since the Object Class is the parent of all Objects there is no issue. But say you wanted to do something like this "Question createdObject = new Object()". This will instantly raise a compiler issue, for though all questions are objects not all objects are questions. And with that we wrap up our crash course on what is a variable/reference and what is an object.

By the third line the equals method is doing a cast of the parameter into a Question Object. What a cast does is it gets the object from a refrence and puts it into a reference of the type referred to inside the parenthesis. This allows us to access the methods specific to the class we casted to. Though comfortable if this is done without a instanceof check the runtime will launch an exception as the attempted cast has no way of converting the original object to the provided reference type.

thats it for pecularities in the equals method. The logic goes something like this:

    if the received object is of type question 
        then 
            if the received parameter's id is the same as this objects id 
                return true. 
            For all other caes return false
            
so now let's make some excercises. Let's create a list and a set of questions of questions. Then we will add a question with the same id to both the list and the question. This should happen in a new controller as it handles a different type of business object. Here is the sample code:

        public ResponseEntity<Map<String,Object>> sayHelloWorld(){
            Map<String,Object> result = new HashMap<>();
            List<Question> questionList = new ArrayList<>();
            Question repeatedQuestion = new Question();
            repeatedQuestion.setId(0L);
            repeatedQuestion.setQuestion("Some new question");
            repeatedQuestion.setResponse("some new Response");
            repeatedQuestion.setLevel("new Level");
            repeatedQuestion.setSeniority("new Seniortiy");
            repeatedQuestion.setSubject("new subject");
            questionList.add(repeatedQuestion);
            for(long i=0; i<7;i++){
                Question newQuestion = new Question();
                newQuestion.setId(i);
                newQuestion.setQuestion("Some question");
                newQuestion.setResponse("some Response");
                newQuestion.setLevel("Level");
                newQuestion.setSeniority("Seniortiy");
                newQuestion.setSubject("subject");
                questionList.add(newQuestion);
            }
            result.put("List",questionList);
            Set<Question> questionSet = new HashSet<>();
            questionSet.add(repeatedQuestion);
            for(long i=0; i<7;i++){
                Question newQuestion = new Question();
                newQuestion.setId(i);
                newQuestion.setQuestion("Some question");
                newQuestion.setResponse("some Response");
                newQuestion.setLevel("Level");
                newQuestion.setSeniority("Seniortiy");
                newQuestion.setSubject("subject");
                questionSet.add(newQuestion);
            }
            result.put("Set",questionSet);
            return ResponseEntity.ok().body(result);
        }
        
 There are a lot of things wrong with that code but for now let's assume thats exactly what we wanted to do. Once you run the app and access the url you should get a result similar to this:
 
    {
        "Set": [
            {
                "id": 1,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 2,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 3,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 4,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 5,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 6,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 0,
                "question": "Some new question",
                "response": "some new Response",
                "subject": "new subject",
                "seniority": "new Seniortiy",
                "level": "new Level"
            }
        ],
        "List": [
            {
                "id": 0,
                "question": "Some new question",
                "response": "some new Response",
                "subject": "new subject",
                "seniority": "new Seniortiy",
                "level": "new Level"
            },
            {
                "id": 0,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 1,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 2,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 3,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 4,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 5,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            },
            {
                "id": 6,
                "question": "Some question",
                "response": "some Response",
                "subject": "subject",
                "seniority": "Seniortiy",
                "level": "Level"
            }
        ]
    }

See how on the list object you have two questions with ID 0 and on the Set you only have the "repeatedquestion" values. This is because lists do not check objects for uniqueness and sets require that each object in them be unique. Though we will not go into the deep end of collections for this step suffice it to know that each collection serves a purpose and as comfortable as using arrays or lists might be you should always look to use the collection that best matches your needs.

## Excercise

Lets do a couple of changes to our existing code to make sure it looks as best as possible and is as reusable as possible.
Try to answer a couple of questions when doing refactors, such as:
    
Is the code repeated? if so, what hardcoded values turned into parameters would allow me to make the repeated code a method?

Can this be used further down the road? if not, what would I need to change to make it so.

Is the code readable?(that is to say it is easy to identify what is happening and fits in a single screen)

1.- Refactor the code to make it as compact as possible without using method stringing.

2.- Change at what point in your code is your repated Question added to the set and see if it affects the output. 
    
    
### About The Suggested Solution

The suggested Solution still has issues but it cuts down on the code length and makes methods reusable for other implememntations we might need. in the next step we will attempt to address some of this issues and work our way out of them while we provide our first CRUD controller.
