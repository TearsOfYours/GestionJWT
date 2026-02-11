package ies.sequeros.dam.pmdm.gestionperifl.ui.components.main

sealed class MainSection(val title: String) {
    object Dashboard : MainSection("Dashboard")
    object DashboardLabels : MainSection("Dashboard Labels")

}