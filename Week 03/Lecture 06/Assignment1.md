<h2>Normalization</h2>

Normalization is a process used in database design to organize tables and minimize redundancy and dependency by dividing larger tables into smaller ones. The goal is to ensure data integrity and reduce anomalies when modifying or querying data.

![image](https://i.ibb.co.com/68F29tS/Screen-Shot-2024-06-28-at-11-48-54.png)

### Purpose of normalization

Normalization is essential to create high-quality database designs that meet desired properties. However, it can be challenging if the underlying principles are not well understood or difficult to detect.

### Levels of normalization

#### 1NF (All attributes depend on the key)
The first normal form defines the basic properties of a table:
1. Eliminate composite attributes, multivalued attributes, and nested relations.
2. Each column should contain atomic (indivisible) values.

**Example**

![image](https://i.ibb.co.com/C55cZSq/Screen-Shot-2024-06-28-at-12-40-34.png)
![answer](https://i.ibb.co.com/xJk1bN6/Screen-Shot-2024-06-28-at-12-41-17.png)

#### 2NF (All attributes depend on the whole key)
The second normal form builds on the first:
1. Ensures that all attributes are fully functionally dependent on the primary key.
2. No non-key attribute should be dependent on only a part of the primary key.
3. Decompose tables to separate entities that fulfill these criteria.

**Example**

![image](https://i.ibb.co.com/bBb4S8r/Screen-Shot-2024-06-28-at-12-38-50.png)

#### 3NF (All attributes depend on nothing but the key)
The third normal form further refines the structure:
1. Eliminates transitive dependencies where a non-key attribute depends on another non-key attribute.
2. Every non-key attribute must be functionally dependent on the primary key.
3. Tables are decomposed into smaller tables to achieve these dependencies.

**Example**

![image](https://i.ibb.co.com/27YnJtH/Screen-Shot-2024-06-28-at-12-34-49.png)

**Notes:**
- For FD X -> Y and Y -> Z, with X as the primary key, we consider this a problem only if Y is not a candidate key.
- If Y is a candidate key, there is no issue with transitive dependencies.

### Summary

The table shows summary of normal form based on primary keys and corresponding normalization.

| Normal Form   | Test                               | Remedy (Normalization)                        |
|---------------|----------------------------------------|------------------------------------------------------------|
| First (1NF)          | No multivalued attributes or nested relations | Form new relations for each multivalued attribute or nested relation               |
| Second (2NF)         | Decompose and set up a new realtion for each partial key with its dependent attribute(s). Make sure to keep a relation with the original primary key and any attributes that are fully functionally dependent on it | Decompose and set up a new realtion for each partial key with its dependent attribute(s). Make sure to keep a relation with the original primary key and any attributes that are fully functionally dependent on it |
| Third (3NF)           |Relation should not have a nonkey attribute functionally determined by another nonkey attribute (or by a set of nonkey attributes). That is, there should be no transitive dependency of a nonkey attribute on the primary key            | Decompose and set up a relation that includes the nonkey attribute(s) that functionally determine(s) other nonkey attribute(s) |
