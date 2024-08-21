# Lecture 21

## Component Lifecycle Hooks

Based on [this](/angular-demo/) project, I defined several component that used when build the project.

### `ngOnInit()`

The ngOnInit() method is invoked once during the component lifecycle, after the `ngOnChanges` method if it was called. It's commonly used for initialization tasks such as fetching data from a server. This hook is preferred over the constructor for tasks that involve input properties because the constructor does not have access to input values.

**Example implementation** on [customer.component.ts](/angular-demo/src/app/customer/customer.component.ts)
```ts
ngOnInit(): void {
    this.retrieveCustomers();
}
```

Beside `ngOnInit()` which remained as the only component implemented on the project, there are many component that commonly used for Angular, they are:

### `ngOnChanges()`

The `ngOnChanges()` method is called whenever an input property changes. It is invoked before `ngOnInit()` and receives a SimpleChanges object which contains the current and previous values of the changed properties. This hook is useful for reacting to changes in input properties.

**Example**
```ts
ngOnChanges(changes: SimpleChanges): void {
    if (changes['inputProperty']) {
        // Handle changes to the input property
    }
}

```

### `ngDoCheck()`

The `ngDoCheck()` method is called during every change detection run, immediately after `ngOnChanges()` and ngOnInit(). It allows you to implement your own change detection logic.

**Example**
```ts
ngDoCheck(): void {
    // Custom change detection logic
}
```

### `ngAfterViewInit()`

The `ngAfterViewInit()` method is called after Angular has fully initialized a component's views and child views. This hook is useful for performing operations that depend on the view being fully initialized.

**Example**
```ts
ngAfterViewInit(): void {
    // Logic to execute after view initialization
}
```

### `ngOnDestroy()`

The `ngOnDestroy()` method is called just before Angular destroys the component. It's used for cleanup logic, such as unsubscribing from observables or detaching event handlers to prevent memory leaks.

**Example**
```ts
ngOnDestroy(): void {
    // Cleanup logic
}
```

## Standalone vs. No-standalone Application
