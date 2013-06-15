package com.mcxiaoke.apptoolkit.fragment;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import com.mcxiaoke.apptoolkit.Constants;
import com.mcxiaoke.apptoolkit.R;
import com.mcxiaoke.apptoolkit.adapter.AppActionsAdapter;
import com.mcxiaoke.apptoolkit.adapter.BaseArrayAdapter;
import com.mcxiaoke.apptoolkit.model.AppAction;
import com.mcxiaoke.apptoolkit.model.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: filemanager
 * Package: com.mcxiaoke.appmanager.fragment
 * User: mcxiaoke
 * Date: 13-6-12
 * Time: 下午10:57
 */
public class AppActionDialogFragment extends BaseDialogFragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private BaseArrayAdapter<AppAction> mArrayAdapter;

    private AppInfo app;

    public static AppActionDialogFragment newInstance(AppInfo app) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_APPINFO, app);
        AppActionDialogFragment fragment = new AppActionDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = getArguments().getParcelable(Constants.EXTRA_APPINFO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mArrayAdapter = new AppActionsAdapter(getActivity(), buildAppActions());
        View root = inflater.inflate(R.layout.fm_appactions, null);
        mListView = (ListView) root.findViewById(android.R.id.list);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(this);
        return root;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setTitle(R.string.action_dialog_title);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (app == null) {
            return;
        }
        dismiss();
        final AppAction action = mArrayAdapter.getItem(position);
        if (R.id.action_view_appsetting == action.id) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + app.packageName));
            startActivity(intent);
        } else if (R.id.action_restart_package == action.id) {
            ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            am.restartPackage(app.packageName);
        } else if (R.id.action_install == action.id) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(app.sourceDir)), "application/vnd.android.package-archive");
            startActivity(intent);
        } else if (R.id.action_uninstall == action.id) {
            Uri packageUri = Uri.parse("package:" + app.packageName);
            Intent uninstallIntent =
                    new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
            startActivity(uninstallIntent);
        }
    }

    private List<AppAction> buildAppActions() {
        List<AppAction> actions = new ArrayList<AppAction>();

        AppAction action = new AppAction(R.id.action_view_appinfo, getString(R.string.action_view_appinfo));
        actions.add(action);
        action = new AppAction(R.id.action_view_appsetting, getString(R.string.action_view_appsetting));
        actions.add(action);
        action = new AppAction(R.id.action_backup_apk, getString(R.string.action_backup_apk));
        actions.add(action);
        action = new AppAction(R.id.action_backup_data, getString(R.string.action_backup_data));
        actions.add(action);
//        action = new AppAction(R.id.action_restore_data, getString(R.string.action_restore_data));
//        actions.add(action);
//        action = new AppAction(R.id.action_view_data, getString(R.string.action_view_data));
//        actions.add(action);
        action = new AppAction(R.id.action_clear_data, getString(R.string.action_clear_data));
        actions.add(action);
        action = new AppAction(R.id.action_restart_package, getString(R.string.action_restart_package));
        actions.add(action);
        action = new AppAction(R.id.action_kill_process, getString(R.string.action_kill_process));
        actions.add(action);

        action = new AppAction(R.id.action_install, getString(R.string.action_install));
        actions.add(action);
        action = new AppAction(R.id.action_uninstall, getString(R.string.action_uninstall));
        actions.add(action);
//        action = new AppAction(R.id.action_slient_install, getString(R.string.action_slient_install));
//        actions.add(action);
//        action = new AppAction(R.id.action_silent_uninstall, getString(R.string.action_silent_uninstall));
//        actions.add(action);
//        action = new AppAction(R.id.action_disable_app, getString(R.string.action_disable_app));
//        actions.add(action);
//        action = new AppAction(R.id.action_enable_app, getString(R.string.action_enable_app));
//        actions.add(action);

//        action = new AppAction(R.id.action_install_system, getString(R.string.action_install_system));
//        actions.add(action);
//        action = new AppAction(R.id.action_uninstall_system, getString(R.string.action_uninstall_system));
//        actions.add(action);
//        action = new AppAction(R.id.action_view_playstore, getString(R.string.action_view_playstore));
//        actions.add(action);
//        action = new AppAction(R.id.action_copy_package, getString(R.string.action_copy_package));
//        actions.add(action);
//        action = new AppAction(R.id.action_view_manifest, getString(R.string.action_view_manifest));
//        actions.add(action);
//        action = new AppAction(R.id.action_view_resource, getString(R.string.action_view_resource));
//        actions.add(action);

        return actions;
    }


}