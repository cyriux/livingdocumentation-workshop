# Pattern Detective

Here is a mini-game of software pattern recognition. Below, you’ll find short descriptions of actual patterns that exist in the software literature, presented here with a made-up name. Some are a little famous, some are hidden gems—used every day in real-world software.

## Your mission:

Read each description, try to guess the pattern’s name, and then look it up to confirm. No spoilers, just clues and a bit of fun!

Think you can spot them all? Let’s go!

## The List of Patterns to Recognize

*Recognize them by their official name in the literature.*

### 1. The Silent Stand-In

Imagine you’re writing code that interacts with an object, but sometimes that object might not exist. Instead of littering your code with `if (obj != null)` checks, you use a special object that behaves like a “ghost”: it’s always there, but does nothing meaningful. It implements the same interface as the real object, so your code doesn’t need to know the difference.

```
RealObject -- implements --> Interface
SilentStandIn -- implements --> Interface
```

**Clue:** This pattern is often used in collections or logging systems. It’s a cousin of the [Strategy pattern](https://en.wikipedia.org/wiki/Strategy_pattern), but for absence.

---

### 2. The Test Chef

Writing tests can be tedious, especially when you need to create complex objects with many fields. Instead of repeating the same setup code everywhere, you create a helper that lets you “cook up” test objects with sensible defaults. You can tweak only the fields you care about for a specific test.

```
TestChef.for(Person)
  .withName("Alice")
  .withAge(30)
  .build();
```

**Clue:** This pattern often makes it of the Fluent interface pattern, but specifically for testing.

---

### 3. The Traveling Suitcase

When you have a chain of method calls, and each method needs a bunch of related data, you don’t want to pass 10 parameters everywhere. Instead, you pack all the data into a single object—a “suitcase” and pass it along. Each method can open the suitcase, take what it needs, and maybe add something new for the next method.

```
MethodA(Suitcase) --> MethodB(Suitcase) --> MethodC(Suitcase)
```

**Clue:** This is not the same as a [Facade](https://en.wikipedia.org/wiki/Facade_pattern), but it’s often used in workflows or pipelines.

---

### 4. The Shared Blueprint 

In some domains, you have many objects that are almost identical, except for a few details. Instead of duplicating all that shared data, you create a single “blueprint” object that holds the common parts. The individual objects just reference the blueprint and store their unique details. 

For example, most insurance contracts share the same base policy terms (e.g., coverage limits, exclusions, deductibles), but differ only in customer-specific details (e.g., driver name, car model, premium). You would model that as a shared "blueprint" object `PolicyTemplate` that ius referenced by many `Contract` objects that stores the customer-specific data (e.g., contractId, driverName, carVIN).

```
Blueprint (shared)
  |
  +-- Instance1 (unique data)
  +-- Instance2 (unique data)
```

**Clue:** This pattern was in an early popular patterns book.

---

### 5. Purely Input-Output Domain Model Behind Adapters

This pattern is a strict, opinionated approach to keep your domain logic is pure and without side effects (see FP) while not depending on anything else, like databases, APIs, UIs etc. Typically adapters (REST controllers, Kafka consumers...) would have to query the DBs, call the domain models while passing everything they need by value, then get the output and store if into the DB. Extra benefit: no need to inject repositories with the DIP!

```
Adapters (I/O, DB, APIs)
       ↓
[Load data] → Domain Model (Pure Logic) → [Save result]
       ↑
       (No dependencies, no side effects!)
```

**Clue:** This is a stricter, more opinionated cousin of [Hexagonal Architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)) for FP connaisseurs.
