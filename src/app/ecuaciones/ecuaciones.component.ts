import { Component, OnInit } from '@angular/core';
import { Equation } from './Equation';
import { EcuacionesService } from './../ecuaciones.service';

@Component({
  selector: 'app-ecuaciones',
  templateUrl: './ecuaciones.component.html',
  styleUrls: ['./ecuaciones.component.css']
})
export class EcuacionesComponent{

  currentEquation: Equation = new Equation();
  equations: Equation[] = [];
  service: any;

  constructor(private ecuacionesService : EcuacionesService) { }

  add(){
    this.equations.push(this.currentEquation)
  }

  remove(equation : Equation){
    for (let i=0; i<this.equations.length; i++){
      if (this.equations[i] === equation){
        this.equations.splice(i, 1)
        break
      }
    }
  }

  generarHamiltoniano() {
    this.ecuacionesService.generarHamiltoniano(this.equations).subscribe(
      result =>{
        alert("Hamiltoniano generado")
      },
      error => {
        alert("Error al generar el Hamiltoniano")
      }
    )
  }
}