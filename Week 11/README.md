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

## Standalone vs. Non-standalone Components

Angular offers two primary ways to structure components: traditional NgModules (no-standalone) and standalone components.

### Non-standalone (NgModules)

Non-standalone components are defined within the context of `NgModules`, which act as containers that **group components, pipes, directives, and services together**. `NgModules` provide a structured way to organize an an application, ensuring that different parts of the application can be loaded, compiled, and executed in a modular approach. This approach has been the traditional method of building Angular applications.

#### Pros:
1. Mature and well-understood: Long-standing with extensive documentation and community support.
2. Scoped injectors: Provides well-defined scopes for dependency injection, beneficial in complex applications.
3. Lazy loading: Facilitates reducing the initial load time of the application.

#### Cons:
1. Boilerplate: Requires additional setup with NgModule definitions, even for simple components.
2. Complexity: May add unnecessary complexity, especially in smaller applications.

**Example**

```ts
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
```

### Standalone

Unlike traditional components that rely on `NgModules`, standalone components are **self-contained and do not need to be declared within an NgModule**. This approach reduces the boilerplate code required to set up an Angular application, making the overall structure more straightforward and modular. Standalone components can potentially improve application performance, especially during the bootstrapping phase by removing unnecessary dependencies and focusing on the essential functionality of each component.

#### Pros:
1. Simplified structure: Eliminates the need for NgModules, reducing boilerplate and making the setup more straightforward.
2. Modularity: Encourages building smaller, modular components that are easily reusable.
3. Performance: Can lead to faster application bootstrapping by removing unnecessary module dependencies.

#### Cons:
1. Compatibility: Not all Angular libraries and tools fully support standalone components yet.
2. Learning curve: Requires familiarity with the latest Angular features and best practices.

**Example**
```ts
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  
  // Login logic 
}

```

### Best Practice

Standalone components in Angular offer several advantages and are particularly useful in specific scenarios, such as standalone components bring a simplified approach to Angular development, self-contained units of functionality that can be easily reused across different parts of an application, reduce dependencies between components and modules, isolating features or functionality, and easier to adapt or change individual components without impacting the overall system.