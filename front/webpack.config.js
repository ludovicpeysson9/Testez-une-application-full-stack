const path = require('path');

module.exports = {
    module: {
        rules: [
            {
                test: /\.(ts|js)$/,  // Instrumenter tous les fichiers ts et js
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
                loader: 'coverage-istanbul-loader',
                options: {
                    esModules: true,
                },
                enforce: 'post',
                include: path.join(__dirname, 'src'), // Assurez-vous que les fichiers source sont correctement inclus
                exclude: [
                    /(node_modules)/,
                    /test/,
                ],
            },
        ],
    },
};
