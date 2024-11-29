import * as path from 'path';

export default {
  module: {
    rules: [
      {
        test: /\.(js|ts)$/,
        loader: '@jsdevtools/coverage-istanbul-loader',
        options: {
          esModules: true,
        },
        enforce: 'post',
        include: path.join(__dirname, '..', 'src'),
        exclude: [
          /\.(e2e|spec)\.ts$/,  // Exclure les fichiers de test
          /node_modules/,
          /(ngfactory|ngstyle)\.js/,
        ],
      },
    ],
  },
};
