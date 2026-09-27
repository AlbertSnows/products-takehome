# How the project was generated

## Backend

The `backend/` skeleton was generated with [Spring Initializr](https://start.spring.io), using its
`starter.zip` endpoint from the command line:

```
curl https://start.spring.io/starter.zip \
  -d type=gradle-project -d language=java -d bootVersion=4.1.1 \
  -d baseDir=backend -d groupId=com.products -d artifactId=takehome \
  -d name=takehome -d packageName=com.products.takehome -d javaVersion=25 \
  -d dependencies=web,data-jpa,postgresql,devtools \
  -o backend.zip
unzip backend.zip -d backend && rm backend.zip
```

Generated: `build.gradle`, `settings.gradle`, the Gradle wrapper, `TakehomeApplication.java`, the
`contextLoads` test, `application.properties`, and `.gitignore`. 

## Frontend

The `frontend/` app was generated with Vite's `create-vite` scaffolding tool, using the plain React
(JavaScript) template:

```
npm create vite@latest frontend -- --template react
cd frontend
npm install
```

The command creates the folder you name, so running it inside an existing `frontend/` folder produced
`frontend/frontend/`. The contents were moved up one level, including the hidden files
(`.gitignore`, `.oxlintrc.json`), and the empty inner folder was removed. Running it from the repo
root avoids the extra step.

Generated:

- `package.json` with the scripts `dev`, `build`, `lint`, and `preview`. Dependencies at generation
  time: `react` and `react-dom` ^19.2.8, `vite` ^8.3.0, `@vitejs/plugin-react` ^6.1.1, `oxlint`
  ^1.81.0, and the `@types/react` and `@types/react-dom` typings.
- `package-lock.json`, written by `npm install`.
- `.oxlintrc.json`, the Oxlint config that the template ships (React and Oxc plugins, with
  `react/rules-of-hooks` as an error).
- `vite.config.js`, `index.html`, `.gitignore`, `README.md`, `public/`, and `src/` (`main.jsx`,
  `App.jsx`, `App.css`, `index.css`).

`src/App.jsx` and the CSS files were the template's demo content. `App.jsx` was rewritten for the
products UI, and `App.css` and `index.css` were deleted, since the exercise says not to spend time on
styling.

### Test tooling

The template does not include a test runner. It was added after generation with:

```
npm install -D vitest @testing-library/react @testing-library/dom @testing-library/jest-dom @testing-library/user-event jsdom
```

Versions at install time: `vitest` ^5.0.2, `@testing-library/react` ^16.3.3, `@testing-library/dom`
^10.4.2, `@testing-library/jest-dom` ^7.0.1, `@testing-library/user-event` ^14.6.7, and `jsdom` ^30.1.1.

- `vitest` is the test runner. It reads `vite.config.js`, so it shares the Vite setup.
- `jsdom` gives the tests a fake browser DOM.
- `@testing-library/react` renders components, and `@testing-library/dom` is its required peer.
- `@testing-library/jest-dom` adds matchers such as `toBeInTheDocument()`.
- `@testing-library/user-event` simulates typing and clicking.

Three further changes wire it up:

- `vite.config.js` gets a `test` block: `environment: 'jsdom'`, `globals: true`, and
  `setupFiles: './tests/setupTests.js'`. `globals: true` is there because React Testing Library only
  cleans up between tests automatically when `afterEach` is a global.
- `tests/setupTests.js` contains one line, `import '@testing-library/jest-dom/vitest'`.
- `package.json` gets the scripts `"test": "vitest"` (watch mode) and `"test:run": "vitest run"`.

Test files live in `tests/`, next to `src/`, and are named `*.test.jsx`, which is the pattern Vitest
looks for. They import the components from `../src/`.

### Written by hand

`src/api.js`, `src/ProductList.jsx`, `src/ProductForm.jsx`, the new `src/App.jsx`, and everything in
`tests/` other than the one-line setup file.

**Sources**

- [Vite: Getting Started](https://vite.dev/guide/)
- [Vite template-react](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react)
- [Oxlint: Configuration](https://oxc.rs/docs/guide/usage/linter/config.html)
- [Vitest: Getting Started](https://vitest.dev/guide/)
- [Vitest: globals](https://vitest.dev/config/globals.html)
- [Testing Library: React setup](https://testing-library.com/docs/react-testing-library/setup/)
- [jest-dom](https://github.com/testing-library/jest-dom)
