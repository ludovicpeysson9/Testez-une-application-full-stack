const path = require('path');

module.exports = {
    module: {
        rules: [
            {
                test: /\.(ts|js)$/, // Instrumenter tous les fichiers ts et js
                use: [
                    {
                        loader: 'ts-loader',
                        options: {
                            transpileOnly: true,
                        },
                    },
                    {
                        loader: '@jsdevtools/coverage-istanbul-loader',
                        options: { esModules: true },
                    },
                ],
                enforce: 'post', // Appliquer après le transpileur
                include: path.join(__dirname, 'src'), // Inclure les fichiers source
                exclude: [
                    /(node_modules)/,
                    /test/,
                ],
            },
        ],
    },
};
