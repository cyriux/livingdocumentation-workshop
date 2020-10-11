Modern Banking in 1500 Microservices, by Matt Heath and Suhail Patel from Monzo bank

*Excerpts from the full article (20mn read): https://www.infoq.com/presentations/monzo-microservices/ Copyright Infoq*

Patel: When we got asked to give this presentation we had around about 1500 microservices. Since time has gone past, we've now got nearly 1600. I checked this morning. Our microservice ecosystem continues to grow. Here's a connected call graph of every one of our services. Each edge represents a service actually calling another service via the network. The number of connections is really large. It's constantly changing as new features are being developed.

# What is Monzo?

Heath: Five years ago, a group of people decided to build a bank from the ground up. There are a lot of reasons for that. We wanted to build something that's a bit different, how people manage their money easily, simply. That means you're competing in a space of, honestly, quite a large number of quite big, very well established banks. When we were trying to work out how we would approach that problem, we wanted to build something that was flexible, and could let our organization flex and scale as we grew.

# The Technology Choices

We use Go as our primary programming language. There's lots of reasons for that. Ultimately, as a language goes, it's quite simple. It's statically typed. It makes it quite easy for us to get people on board. 

Honestly, if you're working in a company where you have quite a large framework, you already have that problem. You have to get people to understand how your toolset works, and how your framework works, and how they can be effective within your organization.

We wanted to pick distributed technologies from very early on. We use Cassandra as our database. Back in 2015 Kubernetes wasn't really an option, so we actually used Mesos. Then a bit later in 2016, we revised that, looked around, and it was clear that Kubernetes was the emerging market leader. 

# Adding a Microservice

Patel: You want to add a microservice? Where do you get started? You start with a blank canvas. This is the surface area that engineers are typically exposed to. They put their business logic in a well-defined box. The surrounding portion makes sure that it works and is production ready, and provides all the necessary interfaces and integrations with the rest of our infrastructure. 

# How To Compose Services Together?

How can we compose services together to form a cohesive product, or offering, or service? We take a problem and subdivide it into a set of bounded context. The whole premise behind this is the single responsibility principle. Take one thing, do it correctly and do it well. Each service provides a well-defined interface.

# Code Generation

Code generation is another avenue where we optimize for engineer productivity, and it gives us a lot of standardization. As you can imagine, we have now 1600 microservices. The number of endpoints that are exposed is really big. We define our API semantics in Protocol Buffers format. Then use code generation tools with our own extensions, we've extended upon the existing tooling available to generate the majority of boilerplate code. You can achieve something like this with gRPC. What this means is that each service is usually about 500 to 1000 lines of actual business logic.

# Standardize Service Naming

Even really simple things and core things like standardizing service naming. Nobody is deploying a service with innuendo names. Each service is well described in its naming. Service structure, the way we restructure files, where do you put particular files within your code, is all standardized. The vast majority of services use a standardized service generator. All this code is generated up front and the sub-structure is generated up front. No matter what team I go into, I know where I can find the database code. It will be in the dao folder. I know where I can find the routing logic. It will be in the handler folder. Queue consumers will be in the consumer folder. This allows for much easier collaboration and onboarding for engineers onto different teams. At Monzo, engineers move around teams really often. We are really a flexible and growing organization. 

# Tooling

If you're working in a language like Go, you can build parsers and understand your existing code, and extract information from code. Go provides this to you right from the standard library. As we've standardized our service structure, we've been able to build tooling and can operate across all of our services. For example, this tool on-screen called service query, which can print out all of the API endpoints for a given service, and prompt it straight from the code. 

# RPC Tracing

Similarly, we've spent a lot of time on our backend to unify our RPC layer, so when a service calls another service, to communicate with each other. This means that trace IDs and context parameters are parsed across service boundaries. From there, we can use technologies like OpenTracing and OpenTelemetry, and open-source tools like Jaeger to provide rich traces of each hop. Here, you can narrow down how long each hop took, and the dependencies on external services and systems. We've baked in Cassandra integration and etcd integration right into the library so that we can visualize all of that in Jaeger.

# On-Boarding

This starts from day one as an engineer. We go through the onboarding flow. We talk about the things that we want people to think about. Have a documented way to bring people up to speed. I think having that training and giving people an easy path to onboard is really important. Then you can rely on shared expertise across the company. We have lots of patterns for solving problems across all these services and within many teams. As engineers join the company and as we grow, we can leverage that repeatedly. Crucially, you don't need to know how 1600 different systems work. Different teams are working on a cluster and those clusters operate very particular systems. Generally, a person will join a team and they will be looking at a very particular thing.
