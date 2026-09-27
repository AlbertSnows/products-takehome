# React + Vite

Notes about various decisions and concepts. 

### jsdom

A fake browser. Used for tests that need a document to render componesnts into. 

## .oslintrc

Oxlint. A linter. 

## setupTests

Adds an import that gives us access to functions suc as
`toBeInTheDocument` and `toHaveValue`

## StrictMode

A tool that re-renders components twice. Mainly used for dev debugging

## createRoot

Allows react to take over the provided element and inject things into it. 

## useState

Refers to the component's memory. 

## preventDefault

By default, submitting a form reloads the page. This prevents that.

## useEffect

