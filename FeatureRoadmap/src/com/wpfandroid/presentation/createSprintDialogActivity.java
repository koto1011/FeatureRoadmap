package com.wpfandroid.presentation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createSprintDialogActivity extends Activity{
	static String sprintName;
	static Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = new Dialog(createSprintDialogActivity.this);
		dialog.setContentView(R.layout.createmilestone);
		dialog.show();
		
		final Button buttonOk = (Button) dialog.findViewById(R.id.ButtonOk);
		buttonOk.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				createSprintDialogActivity.sprintName = (String) ((EditText) dialog.findViewById(R.id.milestoneName)).getText().toString();
				setResult(0, new Intent(
						createSprintDialogActivity.this, 
						DragNDropActivity.class)
							.putExtra("SprintName", createSprintDialogActivity.sprintName
						));
				finish();
			}
		});
		
		final Button buttonCancel = (Button) dialog.findViewById(R.id.ButtonCancel);
		buttonCancel.setOnClickListener(new Button.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(1);
				Log.e("ButtonCancel", "sprintName");
			}
		});
		
	}
	
}
