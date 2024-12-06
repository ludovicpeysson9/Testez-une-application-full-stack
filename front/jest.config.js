module.exports = {
  moduleNameMapper: {
    '@core/(.*)': '<rootDir>/src/app/core/$1',
    '^src/(.*)$': '<rootDir>/src/$1'
  },
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  bail: false,
  verbose: false,
  collectCoverage: true,
  coverageDirectory: './coverage/jest',
  coverageReporters: ['html', 'text-summary'], // Générer des rapports HTML et un résumé texte
  testPathIgnorePatterns: ['<rootDir>/node_modules/', '<rootDir>/src/environments/'],
  coveragePathIgnorePatterns: ['<rootDir>/node_modules/'],
  coverageThreshold: {
    global: {
      statements: 80
    },
  },
  roots: [
    "<rootDir>/src/"
  ],
  modulePaths: [
    "<rootDir>"
  ],
  moduleDirectories: [
    "node_modules"
  ],
};
