import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SubsFormComponent } from './subscription/subs-form/subs-form.component';

const routes: Routes = [
  {
    path: 'app-subs-form',
    component: SubsFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
