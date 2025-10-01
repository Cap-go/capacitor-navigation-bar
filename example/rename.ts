import { MobileProject } from '@trapezedev/project';
import type { MobileProjectConfig } from '@trapezedev/project';

const config: MobileProjectConfig = {
    ios: {
        path: 'ios/App',
    },
    android: {
        path: 'android',
    },
};

async function run() {
    const project = new MobileProject('.', config);
    await project.load();
    const appId = 'com.example.plugin.navigation.bar';

    // Android settings

        await project.android?.setPackageName(appId);
        const appBuildGradleFile =
            await project.android?.getGradleFile('app/build.gradle');
        await appBuildGradleFile?.setApplicationId(appId);
        await appBuildGradleFile?.setNamespace(appId);

    await project.commit();
}

run();